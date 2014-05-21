(function ($) {
    var winMaxHeight, $windWrap;
    var allId = [];
    $.fn.extend({
        window:function (opts) {
            var defaults = {
                id:"", //window的id  必须传入这个值
                content:"", //window的body的内容
                title:"div1", //window的title的内容
                width:300, //window的宽度
                height:200, //window的高度
                top:0, //window的位置 top
                left:0, //window的位置 left
                zIndex:0, //设置初始化的时候哪个窗口在最上边
                draggable:true, // 是否可以拖拽，默认是可以拖动
                resizing:true, //是否可以拖动边框来改变窗口的大小，默认是可以的
                hasIFrame:false, //是否有iframe,这个应该是可以选择的，所以要做成配置文件的样式
                url:"", //是否有ajax调用
                iconMinUrl:"", // 自定义图片的标识
                iconMaxUrl:"",
                iconResUrl:"",
                iconCloseUrl:"",
                diyButton:[],
                hasBgShadow:true
            }
            var defaults = $.extend(defaults, opts);
            var self;
            //必须得传个id， 随便什么id都行
            var $cusWindow = $('<div class="cusWindow" minsize="false" maxsize="false"></div>');
            var $cusWindow_head = $('<div class="cusWindow_head"></div>');
            var $cusWindow_title = $('<div class="cusWindow_title"></div>');
            var $cusWindow_icon = $('<div class="close_window"></div>');
            var $cusWindow_cont = $('<div class="cusWindow_cont"></div>');
            var $iframe = $('<iframe class="if1" name="iframe_name" style="z-index:150;"></iframe>');
            var $shadow = $('<div class="shadow" style="z-index:180"></div>');
            var $left = $('<div class="left"></div>');
            var $right = $('<div class="right"></div>');
            var $top = $('<div class="top"></div>');
            var $bottom = $('<div class="bottom"></div>');
            var $leftTop = $('<div class="left_top"></div>');
            var $rightTop = $('<div class="right_top"></div>');
            var $leftBottom = $('<div class="left_bottom"></div>');
            var $rightBottom = $('<div class="right_bottom"></div>');
            var $bgShadow = $('<div class="bgShadow" style="z-index:10"></div>');
            var scrollWidth = $("html").width() - $("body").width();
            var $ifr;
            var contHeight = 0;//内容的高度
            var x = 0;
            var y = 0;
            var nowX = 0;
            var nowY = 0;
            var temHeight = 0; //最小化的时候记录元素最小化的位置
            var maxTop = 0;
            var maxLeft = 0;
            var maxH = 0;
            var maxW = 0;
            var maxOffsetTop = 0;
            var tem;
            var str;

            init();

            //窗口的初始化工作：包含把各个html元素组装起来，添加相应的标识属性，给相应的标签绑定事件
            function init() {
                if (defaults.id != "") {
                    for (var i = 0; i < allId.length; i++) {
                        if (allId[i] == defaults.id) {
                            return false;
                        }
                    }
                    allId.push(defaults.id);
                    winMaxHeight = defaults.height;
                    $cusWindow.css({"display":"block", "position":"absolute", "width":defaults.width, "height":defaults.height});
                    contHeight = defaults.height - 30;
                    $cusWindow_cont.css({"height":contHeight});
                    $left.css({"height":defaults.height});
                    $right.css({"height":defaults.height});
                    $top.css({"width":defaults.width});
                    $bottom.css({"width":defaults.width});
                    $cusWindow_title.append(defaults.title);
                    $windWrap = $('<div class="windConeter"></div>');
                    if (defaults.hasIFrame == true) {
                        $cusWindow_cont.append($iframe);
                        $iframe.attr("id", defaults.id + "_iframe");
                        $iframe.css({"width":defaults.width, "height":defaults.height - 30});
                        $windWrap.height(winMaxHeight - 66);
                    } else {
                        $cusWindow_cont.append(defaults.content);
                        addMyButton(2);
                    }
                    $cusWindow_cont.attr("id", defaults.id + "_cont");
                    bindFunction();
                    loadResize();
                    $cusWindow_head.append($cusWindow_title).append($cusWindow_icon);
                    $cusWindow.attr("id", defaults.id);
                    $cusWindow.attr("tflag", 0);
                    if (defaults.zIndex != 0) {
                        $cusWindow.css({"z-index":defaults.zIndex});
                    }
                    $cusWindow.append($cusWindow_head).append($cusWindow_cont).append($left).append($right).append($top).append($bottom).append($leftTop).append($rightTop).append($leftBottom).append($rightBottom);
                    $("body").append($cusWindow);
                    self = $("#" + defaults.id);
                    setPositionCenter();
                    if (defaults.url != "") {
                        if (defaults.hasIFrame == true) {
                            tem = window.setInterval(ajaxMethod, 500);
                        } else {
                            noFrameAjaxMethod();
                        }
                    }
                } else {
                    alert("窗口的id参数不能为空！");
                    return false;
                }
                //self.bind("dblclick", changeWinMax);
                if (defaults.iconMaxUrl != "") {
                    $cusWindow_icon.filter(".max_size").css({"background":"url(" + defaults.iconMaxUrl + ")"});
                }
                if (defaults.iconMinUrl != "") {
                    $cusWindow_icon.filter(".min_size").css({"background":"url(" + defaults.iconMinUrl + ")"});
                }
                if (defaults.iconCloseUrl != "") {
                    $cusWindow_icon.filter(".window_close").css({"background":"url(" + defaults.iconCloseUrl + ")"});
                }

                if (defaults.hasBgShadow) {
                    $(document.body).append($bgShadow);
                    /*if($.browser.mozilla)
                     $( document.body ).css({ width: $( window ).width(), height: $( window ).height(), overflow: "hidden" });
                     else {
                     $( 'html' ).css({ 'overflow': "hidden" });
                     $( "body" ).css({ 'width': $( window ).width() - scrollWidth });
                     }*/
                    $bgShadow.css({'width':$.browser.msie && $.browser.version < 8 ? $(document.body).width() : $(document).width(),
                        'height':$.browser.msie && $.browser.version < 8 ? $(document.body).height() : $(document).height()
                    })
                }

            }

            //给标签绑定事件
            function bindFunction() {
                $cusWindow.bind('click', changeZIndex);
                if (defaults.draggable == true) {
                    $cusWindow_head.bind('mousedown', drag);
                }
                if (defaults.resizing == true) {
                    $left.bind('mousedown', resizeLeft);
                    $right.bind('mousedown', resizeRight);
                    $top.bind('mousedown', resizeTop);
                    $bottom.bind('mousedown', resizeBottom);
                    $leftTop.bind('mousedown', resizeLeftTop);
                    $rightTop.bind('mousedown', resizeRightTop);
                    $leftBottom.bind('mousedown', resizeLeftBottom);
                    $rightBottom.bind('mousedown', resizeRightBottom);
                }
                $cusWindow_icon.filter(".min_size").bind("click", changeWinMin);
                $cusWindow_icon.filter(".max_size").bind("click", changeWinMax);
                $cusWindow_icon.filter(".close_window").bind("click", closeWindow);
                //当窗口滚动条滚动的时候，最小化的窗口跟着滚动
//                $(window).scroll(function () {
//                    $(".cusWindow").each(function () {
//                        if ($(this).attr("minSize") == "true") {
//                            var offsetTop = $(window).height() + $(window).scrollTop() - 30;
//                            $(this).animate({top:offsetTop }, { duration:600, queue:false });
//                        }
//                    });
//                });
            }

            //先判断是否有最大化窗口,如果有的话，在拖拽的时候就直接给他最大化的窗口加上遮罩
            function maxWinAddShadow() {
                $(".cusWindow").each(function () {
                    if ($(this).attr("maxsize") == "true") {
                        $(this).find(".cusWindow_cont").append($shadow);
                    }
                });
            }

            function maxWinDelShadow() {
                $(".cusWindow").each(function () {
                    if ($(this).attr("maxsize") == "true") {
                        $(this).find(".shadow").remove();
                    }
                });
            }

            //ajax调用需要执行的方法
            function ajaxMethod() {
                if ($(window.frames["iframe_name"].document).find("body").length > 0) {
                    window.clearInterval(tem);
                    $.ajax({
                        url:defaults.url,
                        dataType:"html",
                        type:"GET",
                        success:function (data) {
                            $ifr = $("#" + defaults.id + "_iframe");
                            $ifr.attr("src", defaults.url);
                            if (defaults.diyButton.length > 0) {
                                $ifr.bind("load", {flagType:1}, addMyButton);
                            }
                            //$ifr = $("#"+ defaults.id + "_iframe");
                            //$ifr.attr("src", defaults.url);
                        },
                        error:function (XMLHttpRequest, errorThrown) {
                            alert("数据加载出错！" + errorThrown);
                        }
                    });
                }
            }

            function addMyButton(event) {
                //ddddd
                var $btnBox = $('<div class="form_tool add_peo"></div>');
                if (event.data != undefined) {
                    $("#" + defaults.id + "_iframe").contents().find("body").wrapInner($windWrap).append($btnBox);
                } else {
                    $cusWindow_cont.append($btnBox);
                }
                for (var i = 0; i < defaults.diyButton.length; i++) {
                    var btId = defaults.diyButton[i].id;
                    var btClass = defaults.diyButton[i].btClass;
                    var onclickEvent = defaults.diyButton[i].onclickEvent;
                    var btName = defaults.diyButton[i].value;
                    /*var btFun = defaults.diyButton[i].btFun;
                     var $inputButton = $("<input type='button' class='inp_btn' onclick='"+onclickEvent+"()' />");*/
                    var btFun = defaults.diyButton[i].btFun;
                    var $inputButton = $("<input type='button' class='inp_btn' />");
                    if (btId != undefined) {
                        $inputButton.attr("id", btId);
                    }
                    if (btClass != undefined) {
                        $inputButton.addClass(btClass);
                    }
                    if (btName != undefined) {
                        $inputButton.attr("value", btName);
                    }
                    if (btFun != undefined) {
                        $inputButton.bind("click", btFun);
                    }
                    $btnBox.append($inputButton);
                }
            }

            function noFrameAjaxMethod() {
                $.ajax({
                    url:defaults.url,
                    dataType:"html",
                    type:"get",
                    success:function (data) {
                        $cusWindow_cont.prepend(data);
                    },
                    error:function (XMLHttpRequest, errorThrown) {
                        alert("数据加载出错！" + errorThrown);
                    }
                });
            }


            //实现窗口的最小化和还原
            function changeWinMin() {
                var winHeight = 0;
                var winWidth = 0;
                if ($(this).attr("class") == "min_size") {
                    self.find(".cusWindow_head").unbind('mousedown', drag).bind('click', changeWinMin);
                    var allWidth = 0;
                    var count = 0;
                    var width = 300;
                    $(".cusWindow").each(function () {
                        count++;
                        if ($(this).attr("minSize") == "true") {
                            allWidth = allWidth + $(this).width() + 3;
                        }
                    });
                    if (self.find(".res_size").length > 0) {
                        self.find(".res_size").unbind("mousedown", changeWinMax).bind("mousedown", changeWinMin);
                        self.find(".min_size").hide();
                        self.attr("minsize", "true");
                        self.attr("maxsize", "false")
                        winWidth = $(window).width();
                        winHeight = $(window).height() + $(window).scrollTop() - 30;
                    } else {
                        self.find(".min_size").removeClass("min_size").addClass("res_size").removeAttr("style");
                        if (defaults.iconResUrl != "") {
                            self.find(".res_size").css({"background":"url(" + defaults.iconResUrl + ")"});
                        }
                        self.find(".max_size").hide();
                        self.attr("minsize", "true");
                        self.attr("maxsize", "falze");
                        winWidth = $(window).width();
                        winHeight = $(window).height() + $(window).scrollTop() - 30;
                    }
                    hideElement();
                    defaults.height = self.height();
                    defaults.width = self.width();
                    self.attr("tflag", self.height());
                    defaults.top = self.offset().top;
                    defaults.left = self.offset().left;
                    if (allWidth + width > winWidth) {
                        width = winWidth / count;
                        var tem = 0;
                        $(".cusWindow").each(function () {
                            if ($(this).attr("minsize") == "true") {
                                $(this).css({"width":width - 8, "left":tem, "height":30, "top":winHeight});
                                tem = tem + width - 5;
                            }
                        });
                        return false;
                    }
                    self.css({"height":30, "width":width, "left":allWidth, "top":winHeight});
                    return false;
                } else {
                    if (self.attr("tflag") != "0") {
                        self.css({"z-index":"300"});
                        self.find(".cusWindow_head").unbind('click', changeWinMin).bind('mousedown', drag);
                        showElement();
                        if (self.find(".min_size").length > 0) {
                            self.find(".res_size").unbind("mousedown", changeWinMin).bind("mousedown", changeWinMax);
                            self.find(".min_size").show();
                            self.attr("minsize", "false");
                            winHeight = $(window).height();
                            winWidth = $(window).width();
                        } else {
                            self.find(".res_size").removeClass("res_size").addClass("min_size").removeAttr("style");
                            if (defaults.iconMinUrl != "") {
                                self.find(".min_size").css({"background":"url(" + defaults.iconMinUrl + ")"});
                            }
                            self.find(".max_size").show();
                            self.attr("minsize", "false");
                        }
                        self.css({"height":defaults.height, "top":defaults.top, "left":defaults.left, "width":defaults.width});
                        if (defaults.hasIFrame == true)
                            $("#" + defaults.id + "_iframe").css({"height":defaults.height - 30, "width":defaults.width});
                        //下面的代码是给没有还原的最小化的窗口重新排列，包含宽度，位置
                        var allWidth = 0;
                        var count = 0;
                        var width = self.width();
                        var winWidth = $(window).width();
                        $(".cusWindow").each(function () {
                            if ($(this).attr("minSize") == "true") {
                                count++;
                            }
                        });
                        width = winWidth / count;
                        if (width > 300) {
                            width = 300;
                        }
                        var tem = 0;
                        $(".cusWindow").each(function () {
                            if ($(this).attr("minSize") == "true") {
                                $(this).css({"left":tem, "width":width - 6});
                                tem = tem + width - 3;
                            }
                        });
                        self.attr("theight", 0);
                        return false;
                    }
                }
            }

            //实现窗口的最大化和还原
            function changeWinMax() {
                var winHeight = 0;
                var winWidth = 0;
                var temWidth;
                var scrollWidth = $("html").width() - $("body").width();
                var maxOffsetTop = document.documentElement.scrollTop;
                if (self.find(".max_size").length > 0) {
                    self.css({"z-index":"300"});
                    maxOffsetTop = $(window).scrollTop();
                    maxTop = self.offset().top;
                    if ($.browser.mozilla) {
                        $(document.body).css({ width:$(window).width(), height:$(window).height(), overflow:"hidden" });
                    } else {
                        $('html').css({ 'overflow':"hidden" });
                        $("body").css({ 'width':$(window).width() - scrollWidth });
                    }
                    $(".cusWindow").each(function () {
                        if ($(this).attr("minSize") == "true") {
                            $(this).css({"top":$(window).height() + maxOffsetTop - 30});
                        }
                    });
                    winHeight = $(window).height();
                    winWidth = $(window).width();
                    $left.hide();
                    $right.hide();
                    $bottom.hide();
                    $top.hide();
                    $leftTop.hide();
                    $rightTop.hide();
                    $leftBottom.hide();
                    $rightBottom.hide();
                    $cusWindow_head.unbind('mousedown', drag);
                    var maxHeight = winHeight;
                    maxLeft = self.offset().left;
                    maxW = self.width();
                    maxH = self.height();
                    $(".cusWindow").each(function () {
                        if ($(this).attr("minsize") == "true") {
                            maxHeight = maxHeight - 30;
                            return false;
                        }
                    });
                    $cusWindow_icon.filter(".max_size").removeClass("max_size").addClass("res_size").removeAttr("style");
                    if (defaults.iconResUrl != "") {
                        self.find(".res_size").css({"background":"url(" + defaults.iconResUrl + ")"});
                    }
                    self.css({"width":winWidth, "height":maxHeight - 2, "top":$(window).scrollTop(), "left":"0"}).attr("maxsize", "true");
                    $cusWindow_cont.css({"height":maxHeight - 32});
                    if (defaults.hasIFrame == true)
                        $("#" + defaults.id + "_iframe").css({"height":maxHeight - 32, "width":winWidth});
                } else {
                    self.css({"z-index":"300"});
                    if ($.browser.mozilla)
                        $(document.body).css({ width:"", height:"", 'overflow':"" });
                    else
                        $('html').css({ 'overflow':"" });
                    winHeight = $(window).height();
                    winWidth = $(window).width();
                    $left.show();
                    $right.show();
                    $bottom.show();
                    $top.show();
                    $leftTop.show();
                    $rightTop.show();
                    $leftBottom.show();
                    $rightBottom.show();
                    $cusWindow_head.bind('mousedown', drag);
                    self.find(".res_size").removeClass("res_size").addClass("max_size").removeAttr("style");
                    if (defaults.iconMaxUrl != "") {
                        self.find(".max_size").css({"background":"url(" + defaults.iconMaxUrl + ")"});
                    }
                    self.css({"top":maxTop, "left":maxLeft, "height":maxH, "width":maxW}).attr("maxsize", "false");
                    $cusWindow_cont.css({"height":maxH - 30});
                    if (defaults.hasIFrame == true)
                        $("#" + defaults.id + "_iframe").css({"height":maxH - 30, "width":maxW});
                }
            }

            //隐藏需要隐藏的元素
            function hideElement() {
                $cusWindow_cont.hide();
                $left.hide();
                $right.hide();
                $bottom.hide();
                $top.hide();
                $leftTop.hide();
                $rightTop.hide();
                $leftBottom.hide();
                $rightBottom.hide();
            }

            //显示需要显示的元素
            function showElement() {
                $cusWindow_cont.show();
                $left.show();
                $right.show();
                $bottom.show();
                $top.show();
                $leftTop.show();
                $rightTop.show();
                $leftBottom.show();
                $rightBottom.show();
            }

            //窗口在没有top和lef的情况下默认居中
            function setPositionCenter() {
                var winHeight = $(window).height();
                var winWidth = $(window).width();
                if (defaults.top == 0) {
                    var temTop = (winHeight - defaults.height) / 2;
                    self.css({"top":temTop});
                } else {
                    self.css({"top":defaults.top});
                }
                if (defaults.left == 0) {
                    var temLeft = (winWidth - defaults.width) / 2;
                    self.css({"left":temLeft});
                } else {
                    self.css({"left":defaults.left});
                }
            }

            //阻止默认事件的执行和事件的冒泡，其实我感觉在IE浏览器中还是return false好用
            function stopEventExecute(e) {
                if (e && e.preventDefault) {
                    e.preventDefault();
                    e.stopPropagation();
                } else {
                    //IE中阻止函数器默认动作的方式
                    window.event.returnValue = false;
                    window.event.cancelBubble = true;
                }
            }

            //左侧的resize优化完毕
            function resizeLeft(e) {
                maxWinAddShadow();
                document.getElementsByTagName("body")[0].onselectstart = function () {
                    return false;
                };
                if (defaults.hasIFrame == true) {
                    $cusWindow_cont.append($shadow);
                }
                e = e || window.event;
                stopEventExecute(e);
                x = e.pageX;
                y = e.pageY;
                var right = $(window).width() - (self.width() + self.offset().left + 2);
                $cusWindow.css({"left":""});
                $cusWindow.css({"right":right});
                $(document).bind('mousemove', resizingLeft);
                $(document).bind('mouseup', function () {
                    $(document).unbind('mousemove', resizingLeft);
                    document.getElementsByTagName("body")[0].onselectstart = null;
                    self.find(".shadow").remove();
                });
                return false;
                function resizingLeft(e) {
                    nowX = e.pageX;
                    var width = parseFloat(x - nowX + self.width());
                    if (e.pageX < 3) {
                        width = width - 2;
                        $(document).unbind('mousemove', resizingLeft);
                    }
                    if (width < 150) {
                        width = 150;
                        $(document).unbind('mousemove', resizingLeft);
                    }
                    x = e.pageX, y = e.pageY;
                    $bottom.css({"width":width});
                    $top.css({"width":width});
                    self.css({"width":width});
                    defaults.hasIFrame == true ? $ifr.css({"width":width}) : 5;
                    return false;
                }
            }

            //右侧的resize优化完毕
            function resizeRight(e) {
                maxWinAddShadow();
                document.getElementsByTagName("body")[0].onselectstart = function () {
                    return false;
                };
                defaults.hasIFrame == true ? $cusWindow_cont.append($shadow) : 5;
                e = e || window.event;
                stopEventExecute(e);
                x = e.pageX;
                y = e.pageY;
                var winWidth = $(window).width();
                var left = self.offset().left;
                $cusWindow.css({"right":""});
                $cusWindow.css({"left":left});
                $(document).bind('mousemove', resizingRight);
                $(document).bind('mouseup', function () {
                    $(document).unbind('mousemove', resizingRight);
                    document.getElementsByTagName("body")[0].onselectstart = function () {
                        return false;
                    };
                    self.find(".shadow").remove();
                });
                return false;
                function resizingRight(e) {
                    nowX = e.pageX;
                    var width = parseFloat(nowX - x + self.width());
                    if (e.pageX + 3 > winWidth) {
                        width = width - 2;
                        $(document).unbind('mousemove', resizingRight);
                    }
                    if (width < 150) {
                        width = 150;
                        $(document).unbind('mousemove', resizingRight);
                    }
                    x = e.pageX, y = e.pageY;
                    defaults.hasIFrame == true ? $ifr.css({"width":width}) : 5;
                    $bottom.css({"width":width});
                    $top.css({"width":width});
                    self.css({"width":width});
                    return false;
                }
            }

            //上边框的resize优化完毕
            function resizeTop(e) {
                maxWinAddShadow();
                document.getElementsByTagName("body")[0].onselectstart = function () {
                    return false;
                };
                e = e || window.event;
                stopEventExecute(e);
                x = e.pageX;
                y = e.pageY;
                var obj = self.find(".cusWindow_cont");
                var bottom = $(window).height() - (self.offset().top + self.height() + 2);
                $cusWindow.css({"top":""});
                $cusWindow.css({"bottom":bottom});
                $(document).bind('mousemove', resizingTop);
                $(document).bind('mouseup', function () {
                    $(document).unbind('mousemove', resizingTop);
                    document.getElementsByTagName("body")[0].onselectstart = null;
                });
                return false;
                function resizingTop(e) {
                    nowY = e.pageY;
                    if (e.pageY - $(window).scrollTop() < 3) {
                        $(document).unbind('mousemove', resizingTop);
                        return false;
                    }
                    var vHeight = parseFloat(y - nowY + self.height(), 10);
                    if (vHeight < 100) {
                        vHeight = 100;
                        $(document).unbind('mousemove', resizingTop);
                    }
                    x = e.pageX, y = e.pageY;
                    $left.css({"height":vHeight});
                    $right.css({"height":vHeight});
                    $cusWindow_cont.css({"height":vHeight - 30});
                    self.css({"height":vHeight});
                    //defaults.hasIFrame == true ? $ifr.css({"height":vHeight - 30});$windWrap.height() : 5;
                    if (defaults.hasIFrame == true) {
                        $ifr.css({"height":vHeight - 30});
                        $("#" + defaults.id + "_iframe").contents().find("body > div.windConeter").height(vHeight - 66);
                    } else {
                        5
                    }
                    //fffffffff
                    return false;
                }
            }

            //下边框优化完毕
            function resizeBottom(e) {
                maxWinAddShadow();
                defaults.hasIFrame == true ? $cusWindow_cont.append($shadow) : 5;
                var winHeight = $(window).height();
                e = e || window.event;
                stopEventExecute(e);
                x = e.pageX;
                y = e.pageY;
                var top = self.offset().top;
                $cusWindow.css({"bottom":""});
                $cusWindow.css({"top":top});
                $(document).bind('mousemove', resizingBottom);
                $(document).bind('mouseup', function () {
                    $(document).unbind('mousemove', resizingBottom);
                    self.find(".shadow").remove();
                });
                return false;
                function resizingBottom(e) {
                    nowY = e.pageY;
                    var height = parseFloat(nowY - y + self.height());
                    if ($(window).height() - (e.pageY - $(window).scrollTop()) < 3) {
                        $(document).unbind('mousemove', resizingBottom);
                        return false;
                    }
                    if (height < 100) {
                        vHeight = 100;
                        $(document).unbind('mousemove', resizingBottom);
                    }
                    x = e.pageX, y = e.pageY;
                    $left.css({"height":height});
                    $right.css({"height":height});
                    $cusWindow_cont.css({"height":height - 30});
                    defaults.hasIFrame == true ? $ifr.css({"height":height - 30}) : 5;
                    if (defaults.hasIFrame == true) {
                        $ifr.css({"height":height - 30});
                        $("#" + defaults.id + "_iframe").contents().find("body > div.windConeter").height(vHeight - 66);
                    } else {
                        5
                    }
                    ;
                    self.css({"height":height});
                    return false;
                }
            }

            //左下边框的resize优化完毕
            function resizeLeftBottom(e) {
                maxWinAddShadow();
                document.getElementsByTagName("body")[0].onselectstart = function () {
                    return false;
                };
                defaults.hasIFrame == true ? $cusWindow_cont.append($shadow) : 5;
                var winHeight = $(window).height();
                var winWidth = $(window).width();
                e = e || window.event;
                stopEventExecute(e);
                x = e.pageX;
                y = e.pageY;
                var top = self.offset().top;
                var right = $(window).width() - (self.width() + self.offset().left + 2);
                $cusWindow.css({"left":"", "bottom":""});
                $cusWindow.css({"top":top, "right":right});
                $(document).bind('mousemove', resizingLeftBottom);
                $(document).bind('mouseup', function () {
                    document.getElementsByTagName("body")[0].onselectstart = null;
                    $(document).unbind('mousemove', resizingLeftBottom);
                    self.find(".shadow").remove();
                });
                return false;
                function resizingLeftBottom(e) {
                    nowX = e.pageX;
                    nowY = e.pageY;
                    var width = parseFloat(x - nowX + self.width());
                    var height = parseFloat(nowY - y + self.height());
                    x = e.pageX, y = e.pageY;
                    width < 150 ? width = 150 : width;
                    height < 100 ? height = 100 : height;
                    if ($(window).height() - (e.pageY - $(window).scrollTop()) < 3) {
                        $(document).unbind('mousemove', resizingLeftBottom);
                        return false;
                    }
                    if (e.pageX < 3) {
                        width = width - 2;
                        $(document).unbind('mousemove', resizingLeftBottom);
                        self.attr("onselectstart", "");
                    }
                    $top.css({"width":width});
                    $bottom.css({"width":width});
                    $left.css({"height":height});
                    $right.css({"height":height});
                    $cusWindow_cont.css({"height":height - 30});
                    defaults.hasIFrame == true ? $ifr.css({"height":height - 30, "width":width}) : 5;
                    if (defaults.hasIFrame == true) {
                        $ifr.css({"height":height - 30, "width":width})
                        $("#" + defaults.id + "_iframe").contents().find("body > div.windConeter").css({"height":height - 66, "width":width});
                    } else {
                        5
                    }
                    ;
                    self.css({"width":width, "height":height});
                    return false;
                }
            }

            function resizeRightBottom(e) {
                maxWinAddShadow();
                document.getElementsByTagName("body")[0].onselectstart = function () {
                    return false;
                };
                defaults.hasIFrame == true ? $cusWindow_cont.append($shadow) : 5;
                var winHeight = $(window).height();
                var winWidth = $(window).width();
                e = e || window.event;
                stopEventExecute(e);
                x = e.pageX;
                y = e.pageY;
                var top = self.offset().top;
                var right = self.offset().left;
                $cusWindow.css({"right":"", "bottom":""});
                $cusWindow.css({"top":top, "left":right});
                $(document).bind('mousemove', resizingRightBottom);
                $(document).bind('mouseup', function () {
                    $(document).unbind('mousemove', resizingRightBottom);
                    document.getElementsByTagName("body")[0].onselectstart = null;
                    self.find(".shadow").remove();
                });
                function resizingRightBottom(e) {
                    nowX = e.pageX;
                    nowY = e.pageY;
                    var width = parseFloat(nowX - x + self.width());
                    var height = parseFloat(nowY - y + self.height());
                    x = e.pageX, y = e.pageY;
                    width < 150 ? width = 150 : width;
                    height < 100 ? height = 100 : height;
                    if ($(window).height() - (e.pageY - $(window).scrollTop()) < 3) {
                        $(document).unbind('mousemove', resizingRightBottom);
                        return false;
                    }
                    if (e.pageX + 3 > winWidth) {
                        width = width - 2;
                        $(document).unbind('mousemove', resizingRightBottom);
                    }
                    $top.css({"width":width});
                    $bottom.css({"width":width});
                    $left.css({"height":height});
                    $right.css({"height":height});
                    $cusWindow_cont.css({"height":height - 30});
                    if (defaults.hasIFrame == true) {
                        $ifr.css({"height":height - 30, "width":width})
                        $("#" + defaults.id + "_iframe").contents().find("body > div.windConeter").css({"height":height - 66, "width":width});
                    } else {
                        5
                    }
                    ;
                    self.css({"width":width, "height":height});
                    return false;
                }
            }

            //左上边框的resize基本优化完毕
            function resizeLeftTop(e) {
                maxWinAddShadow();
                document.getElementsByTagName("body")[0].onselectstart = function () {
                    return false;
                };
                defaults.hasIFrame == true ? $cusWindow_cont.append($shadow) : 5;
                var winHeight = $(window).height();
                var winWidth = $(window).width();
                e = e || window.event;
                stopEventExecute(e);
                x = e.pageX;
                y = e.pageY;
                var right = $(window).width() - (self.width() + self.offset().left + 2);
                var bottom = $(window).height() - (self.height() + self.offset().top + 2);
                $cusWindow.css({"left":"", "top":""});
                $cusWindow.css({"bottom":bottom, "right":right});
                $(document).bind('mousemove', resizingLeftTop);
                $(document).bind('mouseup', function () {
                    $(document).unbind('mousemove', resizingLeftTop);
                    document.getElementsByTagName("body")[0].onselectstart = null;
                    self.find(".shadow").remove();
                });
                return false;
                function resizingLeftTop(e) {
                    nowX = e.pageX;
                    nowY = e.pageY;
                    var width = parseFloat(x - nowX + self.width());
                    var height = parseFloat(y - nowY + self.height());
                    x = e.pageX, y = e.pageY;
                    width < 150 ? width = 150 : width;
                    height < 100 ? height = 100 : height;
                    if (e.pageY - $(window).scrollTop() < 3) {
                        $(document).unbind('mousemove', resizingLeftTop);
                        return false;
                    }
                    if (e.pageX < 3) {
                        width = width - 2;
                        $(document).unbind('mousemove', resizingLeftTop);
                    }
                    $top.css({"width":width});
                    $bottom.css({"width":width});
                    $left.css({"height":height});
                    $right.css({"height":height});
                    $cusWindow_cont.css({"height":height - 30});
                    if (defaults.hasIFrame == true) {
                        $ifr.css({"height":height - 30, "width":width})
                        $("#" + defaults.id + "_iframe").contents().find("body > div.windConeter").css({"height":height - 66, "width":width});
                    } else {
                        5
                    }
                    ;
                    self.css({"width":width, "height":height});
                    return false;
                }
            }

            //右上边框的resize优化完毕
            function resizeRightTop(e) {
                maxWinAddShadow();
                document.getElementsByTagName("body")[0].onselectstart = function () {
                    return false;
                };
                defaults.hasIFrame == true ? $cusWindow_cont.append($shadow) : 5;
                var winHeight = $(window).height();
                var winWidth = $(window).width();
                e = e || window.event;
                stopEventExecute(e);
                x = e.pageX;
                y = e.pageY;
                var left = self.offset().left;
                var bottom = $(window).height() - (self.height() + self.offset().top + 2);
                $cusWindow.css({"right":"", "top":""});
                $cusWindow.css({"bottom":bottom, "left":left});
                $(document).bind('mousemove', resizingRightTop);
                $(document).bind('mouseup', function () {
                    $(document).unbind('mousemove', resizingRightTop);
                    document.getElementsByTagName("body")[0].onselectstart = null;
                    self.find(".shadow").remove();
                });
                return false;
                function resizingRightTop(e) {
                    nowX = e.pageX;
                    nowY = e.pageY;
                    var width = parseFloat(e.pageX - x + self.width());
                    var height = parseFloat(y - e.pageY + self.height());
                    x = e.pageX, y = e.pageY;
                    width < 150 ? width = 150 : width;
                    height < 100 ? height = 100 : height;
                    if (e.pageY - $(window).scrollTop() < 3) {
                        $(document).unbind('mousemove', resizingRightTop);
                        return false;
                    }
                    if (e.pageX + 3 > winWidth) {
                        width = width - 2;
                        $(document).unbind('mousemove', resizingRightTop);
                    }
                    $top.css({"width":width});
                    $bottom.css({"width":width});
                    $left.css({"height":height});
                    $right.css({"height":height});
                    $cusWindow_cont.css({"height":height - 30});
                    if (defaults.hasIFrame == true) {
                        $ifr.css({"height":height - 30, "width":width})
                        $("#" + defaults.id + "_iframe").contents().find("body > div.windConeter").css({"height":height - 66, "width":width});
                    } else {
                        5
                    }
                    ;
                    self.css({"width":width, "height":height});
                    return false;
                }
            }

            //拖拽的函数
            function drag(e) {
                maxWinAddShadow();
                document.getElementsByTagName("body")[0].onselectstart = function () {
                    return false;
                };
                defaults.hasIFrame == true ? $cusWindow_cont.append($shadow) : 5;
                e = e || window.event;
                x = e.pageX;
                y = e.pageY;
                var maxX = $(document).width() - 5,
                    maxY = $(document).height() - 5;
                changeZIndex();
                var obj = self;
                $(document).bind('mousemove', dragging);
                $(document).bind('mouseup', function () {
                    $(document).unbind('mousemove', dragging);
                    document.getElementsByTagName("body")[0].onselectstart = null;
                    self.find(".cusWindow_head").css({"cursor":""});
                    self.find(".shadow").remove();
                });
                return false;
                function dragging(e) {
                    self.find(".cusWindow_head").css({"cursor":"move"});
                    var top = 0;
                    var left = 0;
                    if (e.pageY - $(window).scrollTop() < 3) {
                        $(document).unbind('mousemove', dragging);
                        return false;
                    }
                    if ($(window).height() - (e.pageY - $(window).scrollTop()) < 20) {
                        $(document).unbind('mousemove', dragging);
                        return false;
                    }
                    if (e.pageX < 15 || e.pageX > $(window).width() - 15) {
                        $(document).unbind('mousemove', dragging);
                        return false;
                    }
                    left = parseFloat($(obj).position().left + (e.pageX < 15 ? 0 : e.pageX - x), 10);
                    top = parseFloat($(obj).position().top + (e.pageY < 15 ? 0 : e.pageY - y), 10);
                    $(obj).css({
                        'left':e.pageX < 5 || left < 5 ? 0 : left + obj.width() > maxX ? maxX - obj.width() : left,
                        'top':e.pageY < 5 || top < 5 ? 0 : top + obj.height() > maxY ? maxY - obj.height() : top
                    });
                    //todo: 优化移动，实现拖拽页面滚动效果
                    x = e.pageX, y = e.pageY;
                    return false;
                }
            }

            //关闭窗口方法
            function closeWindow() {
                for (var i = 0; i < allId.length; i++) {
                    if (allId[i] == defaults.id) {
                        allId.splice(i, 1);
                    }
                }
                /*if($.browser.mozilla)
                 $( document.body ).css({ width: "", height: "", 'overflow': "" });
                 else
                 $( 'html' ).css({ 'overflow': "" });*/
                $bgShadow.remove();
                self.remove();
            }

            function changeZIndex() {
                $(".cusWindow").css({"z-index":"99"});
                self.css({"z-index":"200"});
            }

            //当window变化时，重定位body,让body的提示框始终居中
            function loadResize() {
                $(window).resize(function () {
                    $bgShadow.css({'width':$.browser.msie && $.browser.version < 8 ? $(document.body).width() : $(document).width(),
                        'height':$.browser.msie && $.browser.version < 8 ? $(document.body).height() : $(document).height()
                    })

                    var winHeight = parseFloat($(window).height(), 10);
                    var winWidth = parseFloat($(window).width(), 10);
                    var maxOffsetTop = document.documentElement.scrollTop;
                    var top = winHeight - 30;
                    var allWidth = 0;
                    $(".cusWindow").each(function () {
                        if ($(this).attr("minsize") == "true") {
                            $(this).css({"top":top});
                            allWidth = allWidth + $(this).width();
                        }
                    });
                    var count = 0;
                    var width = self.width();
                    if (allWidth > winWidth) {
                        $(".cusWindow").each(function () {
                            if ($(this).attr("minsize") == "true") {
                                count++;
                            }
                        });
                        width = winWidth / count;
                        if (width > 300) {
                            width = 300;
                        }
                        var tem = 0;
                        $(".cusWindow").each(function () {
                            if ($(this).attr("minsize") == "true") {
                                $(this).css({"left":tem, "width":width - 6});
                                tem = tem + width - 3;
                            }
                        });
                    }

                    $(".cusWindow").each(function () {
                        if ($(this).attr("minSize") == "true") {
                            $(this).css({"top":$(window).height() + maxOffsetTop - 30});
                        }
                    });

                    tem = 0;
                    $(".cusWindow").each(function () {
                        if ($(this).attr("minsize") == "true") {
                            tem++;
                            $(".cusWindow").each(function () {
                                if ($(this).attr("maxsize") == "true") {

                                    $(this).css({"height":winHeight - 30, "width":winWidth});
                                    $(this).find(".cusWindow_cont").css({"height":winHeight - 60});
                                    return false;
                                }
                            });
                        }
                    });
                    if (tem == 0) {
                        $(".cusWindow").each(function () {
                            if ($(this).attr("maxSize") == "true") {
                                $(this).css({"height":winHeight - 2, "width":winWidth - 5});
                                $(this).find(".cusWindow_cont").css({"height":winHeight - 32});
                            }
                        });
                    }

                });
            }

            //它可以回调的几个方法
            return {
                getWindow:function () {
                    return self;
                },
                getContentId:function () {
                    return $cusWindow_cont.attr("id");
                },
                getIframeId:function () {
                    return $iframe.attr("id");
                },
                addCloseCallBack:function (fun, position) {
                    if ($.isFunction(fun)) {
                        if (position == "after") {
                            $cusWindow_icon.filter(".close_window").bind("click", fun);
                        } else {
                            $cusWindow_icon.filter(".close_window").bind({mousedown:fun});
                        }
                    } else {
                        alert("addCloseCallBack方法的参数不能为空或非函数参数！")
                    }
                    ;
                },
                closeWin:function () {
                    closeWindow();
                },
                refreshContent:function (arg) {
                    var top = self.offset().top;
                    var left = self.offset().left;
                    var height = self.height();
                    var width = self.width();
                    if (arg != undefined) {
                        defaults.url = arg;
                    }
                    if (defaults.url != "") {
                        if (defaults.hasIFrame == true) {
                            $("#" + defaults.id + "_iframe").contents().find("body").html("");
                            ajaxMethod();
                        } else {
                            noFrameAjaxMethod();
                        }

                    }
                    self.css({"top":top, "left":left, "height":height, "width":width});
                },
                setWidth:function (arg) {
                    defaults.width = arg;
                    self.css({"width":arg});
                },
                setHeight:function (arg) {
                    defaults.height = arg;
                    self.css({"height":arg});
                    self.find(".cusWindow_cont").css({"height":arg - 30});
                },
                setBorderWidth:function (arg) {
                    self.css({"border-width":arg});
                },
                setBorderColor:function (arg) {
                    self.css({"border-color":arg});
                }

            };


        }
    });
})(jQuery);