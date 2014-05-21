(function ($) {
    $.fn.extend({
        layout:function (opts) {
            var defaults = {
                top:{
                    topHeight:60, //�߶�
                    topSwitch:false, //�϶����ܿ��أ�true-����false-��
                    topMin:30, //�϶�����С�߶�
                    topMax:150, //�϶������߶�
                    topHide:false, //��ʾ���ؿ��أ�true-����false-��
                    topMaximize:false      //�Ƿ���󻯣�true/false
                },
                left:{
                    leftWidth:200, //�����
                    leftSwitch:true, //�϶����ܿ��أ�true-����false-��
                    leftMin:100, //�϶�����С���
                    leftMax:300, //�϶��������
                    leftHide:true, //��ʾ���صĿ��أ�true-����false-��
                    leftMaximize:false //�Ƿ���󻯣�true:��󻯣�false�������
                },
                right:{
                    rightWidth:150,
                    rightSwitch:true,
                    rightMin:60,
                    rightMax:230,
                    rightHide:true,
                    rightMaximize:false
                },
                bottom:{
                    bottomHeight:30,
                    bottomSwitch:true,
                    bottomMin:15,
                    bottomMax:100,
                    bottomHide:true,
                    bottomMaximize:false
                },
                center:{
                    cneterMaximize:false
                }
            };
            $('html,body').width('100%').height('100%');
            var defaults = $.extend(true, defaults, opts);
            var top = defaults.top;
            var left = defaults.left;
            var center = defaults.center;
            var right = defaults.right;
            var bottom = defaults.bottom;
            var thisBox = $(this);
            thisBox.css({'overflow':'hidden', 'position':'relative'});
            var header = $("#layout_top");
            var leftBar = $("#layout_left");
            var rightBar = $("#layout_right");
            var footer = $("#layout_bottom");
            var contenter = $("#layout_center");

            var allWidth = thisBox.width();
            var allHeight = thisBox.height();
            var topHeight = top.topHeight;
            var leftWidth = defaults.left.leftWidth;
            var rightWidth = right.rightWidth;
            var bottomHeight = bottom.bottomHeight;
            var cenWidth = allWidth - leftWidth - rightWidth;
            var cenHeight = allHeight - topHeight - bottomHeight;
            var bottomTop = topHeight + cenHeight;

            var topReviseAll = $("<div class='layout_top_resizer'><a href='javascript:void(0)' class='top_up' id='top_resizer'></a></div>"); //ͷ��������ק��div
            var topRevise = $('#top_resizer');
            var topReviseTop = topHeight - 6;

            var leftReviseAll = $("<div class='layout_left_resizer'><a href='javascript:void(0)' class='left_close' id='left_resizer'></a></div>"); //��������ק��div
            var leftRevise = $('#left_resizer');
            var leftReviseL = leftWidth - 4;
            var cenReviseP = cenHeight / 2 - 25;

            var rightReviseAll = $("<div class='layout_right_resizer'><a href='javascript:void(0)' class='right_close' id='right_resizer'></a></div>");//�Ҳ������ק��div
            var rightRevise = $('#right_resizer');

            var bottomReviseAll = $("<div class='layout_bottom_resizer'><a href='javascript:void(0)' class='bottom_up' id='bottom_resizer'></a></div>");//�ײ�������ק��div
            var bottomRevise = $('#bottom_resizer');

            var wrapDiv = $('<div class="wrap_div"></div>');

            var displayDiv;

            var centerOldW;
            var centerOldH;
            var centerOldT;
            var centerOldL;
            var topOldW;
            var topOldH;
            var topOldT;
            var leftOldW;
            var leftOldH;
            var leftOldT;
            var rightOldW;
            var rightOldH;
            var rightOldT;
            var bottomOldW;
            var bottomOldH;
            var bottomOldT;

            if (footer.length == '0') {
                bottomHeight = 0;
            }
            ;
            if (header.length == '0') {
                topHeight = 0;
            }
            ;
            if (leftBar.length == '0') {
                leftWidth = 0;
            }
            ;
            if (rightBar.length == '0') {
                rightWidth = 0;
            }
            ;


            forPane();
            function forPane() {
                cenWidth = allWidth - leftWidth - rightWidth;
                cenHeight = allHeight - topHeight - bottomHeight;
                bottomTop = topHeight + cenHeight;
                cenReviseP = cenHeight / 2 - 25;
                leftReviseL = leftWidth - 4;
                topReviseTop = topHeight - 6;

                if (header.length <= 0) {
                    topHeight = 0
                }
                ;
                if (leftBar.length <= 0) {
                    leftWidth = 0
                }
                ;
                if (rightBar.length <= 0) {
                    rightWidth = 0
                }
                ;
                if (footer.length <= 0) {
                    bottomHeight = 0
                }
                ;
                if (header.length > 0) {
                    header.css({'width':allWidth, 'height':topHeight});
                    topReviseAll.css('top', topReviseTop);
                    if (topRevise.length <= 0) {
                        if (defaults.top.topHide == false) {
                            $('.top_up', topReviseAll).remove();
                        } else {
                            header.wrapInner(wrapDiv);
                            $('div.wrap_div', header).css({'width':allWidth, 'height':topHeight});
                        }
                        ;
                        header.append(topReviseAll);
                        topRevise = $('#top_resizer');
                    }
                    ;

                }
                ;
                if (leftBar.length > 0) {
                    leftBar.css({'top':topHeight, 'left':0, 'width':leftWidth, 'height':cenHeight});
                    if (leftRevise.length <= 0) {
                        if (left.leftHide == false) {
                            $('.left_close', leftReviseAll).remove();
                        } else {
                            leftBar.wrapInner(wrapDiv);
                            $('div.wrap_div', leftBar).css({'width':leftWidth, 'height':cenHeight});
                        }
                        ;
                        leftBar.append(leftReviseAll);
                        leftRevise = $('#left_resizer');
                    }
                    ;

                    leftReviseAll.css({'left':leftReviseL, 'height':cenHeight - cenReviseP, 'padding-top':cenReviseP, 'position':"fixed", 'z-index':"800"});
                }
                ;
                if (rightBar.length > 0) {
                    rightBar.css({'top':topHeight, 'right':0, 'width':rightWidth, 'height':cenHeight});
                    if (rightRevise.length <= 0) {
                        if (right.rightHide == false) {
                            $('.right_close', rightReviseAll).remove();
                        } else {
                            rightBar.wrapInner(wrapDiv);
                            $('div.wrap_div', rightBar).css({'width':rightWidth, 'height':cenHeight});
                        }
                        ;
                        rightBar.append(rightReviseAll);
                        rightRevise = $('#right_resizer');
                    }
                    ;

                    rightReviseAll.css({'height':cenHeight - cenReviseP, 'padding-top':cenReviseP, "position":"fixed", "z-index":"200"});
                }
                ;
                if (footer.length > 0) {
                    footer.css({'top':bottomTop, 'left':0, 'width':allWidth, 'height':bottomHeight});
                    if (bottomRevise.length <= 0) {
                        if (bottom.bottomHide == false) {
                            $('.bottom_up', bottomReviseAll).remove();
                        } else {
                            footer.wrapInner(wrapDiv);
                            $('div.wrap_div', footer).css({'width':allWidth, 'height':bottomHeight});
                        }
                        ;
                        footer.prepend(bottomReviseAll);
                        bottomRevise = $('#bottom_resizer');
                    }
                    ;

                }
                ;
                if (contenter.length > 0) {
                    contenter.css({'top':topHeight, 'left':leftWidth + 1, 'width':cenWidth, 'height':cenHeight});
                }
                ;
            }

            ;

            var _move = false;
            var _x, _y;

            function draggable(e) {
                var thisDiv = $(this);
                var parentBox = $(this).parent();
                var x, y;
                _move = true;
                _x = e.pageX - parseInt(thisDiv.css("left"));
                _y = e.pageY - parseInt(thisDiv.css("top"));
                thisDiv.css({'background':'#000'});
                parentBox.css({'overflow':'visible', 'z-index':99});

                $(document).mousemove(function (e) {
                    if (_move) {
                        x = e.pageX - _x;
                        y = e.pageY - _y;
                        if (thisDiv.attr('class') == 'layout_top_resizer') {
                            thisDiv.css({top:y});
                        } else if (thisDiv.attr('class') == 'layout_left_resizer') {
                            thisDiv.css({left:x});
                        } else if (thisDiv.attr('class') == 'layout_right_resizer') {
                            thisDiv.css({left:x});
                        } else if (thisDiv.attr('class') == 'layout_bottom_resizer') {
                            thisDiv.css({top:y});
                        }
                        ;
                    }
                    ;

                }).mouseup(function () {
                        _move = false;
                        if (thisDiv.attr('class') == 'layout_top_resizer') {
                            if (y > defaults.top.topMax) {
                                topHeight = defaults.top.topMax;
                            } else if (y < defaults.top.topMin) {
                                topHeight = defaults.top.topMin;
                            } else {
                                topHeight = y + 6;
                            }
                            ;
                        } else if (thisDiv.attr('class') == 'layout_bottom_resizer') {
                            if (footer.height() - y > defaults.bottom.bottomMax) {
                                bottomHeight = defaults.bottom.bottomMax;
                            } else if (footer.height() - y < defaults.bottom.bottomMin) {
                                bottomHeight = defaults.bottom.bottomMin;
                            } else {
                                bottomHeight = footer.height() - y;
                            }
                            ;
                        } else if (thisDiv.attr('class') == 'layout_left_resizer') {
                            if (x > defaults.left.leftMax) {
                                leftWidth = defaults.left.leftMax;
                            } else if (x < defaults.left.leftMin) {
                                leftWidth = defaults.left.leftMin;
                            } else {
                                leftWidth = x;
                            }
                            ;
                        } else if (thisDiv.attr('class') == 'layout_right_resizer') {
                            if (rightBar.width() - x > defaults.right.rightMax) {
                                rightWidth = defaults.right.rightMax;
                            } else if (rightBar.width() - x < defaults.right.rightMin) {
                                rightWidth = defaults.right.rightMin;
                            } else {
                                rightWidth = rightBar.width() - x;
                            }
                            ;
                        }
                        $(document).unbind('mousemove mouseup');
                        thisDiv.css('background', 'url(../images/layout_top_bg.gif) no-repeat -999px -999px');
                        parentBox.fadeTo(20, 1).css({'overflow':'hidden', 'z-index':1});

                        forPane();

                        if (thisDiv.attr('class') == 'layout_bottom_resizer') {
                            thisDiv.css({top:0});
                        } else if (thisDiv.attr('class') == 'layout_right_resizer') {
                            thisDiv.css({left:0});
                        } else if (thisDiv.attr('class') == 'layout_left_resizer') {
                            thisDiv.css({left:leftWidth - 4});
                        } else if (thisDiv.attr('class') == 'layout_top_resizer') {
                            thisDiv.css({top:topHeight - 6});
                        }
                        ;
                    });
                return false;
            }

            ;

            $(window).resize(function () {
                allWidth = thisBox.width();
                allHeight = thisBox.height();
                forPane();
                if (header.hasClass('max_start')) {
                    header.css({"width":allWidth, "height":allHeight});
                }
                if (leftBar.hasClass('max_start')) {
                    leftBar.css({"width":allWidth, "height":allHeight, "top":"0px"});
                }
                if (rightBar.hasClass('max_start')) {
                    rightBar.css({"width":allWidth, "height":allHeight, "top":"0px"});
                }
                if (footer.hasClass('max_start')) {
                    footer.css({"width":allWidth, "height":allHeight, "top":"0px"});
                }
                if (contenter.hasClass('max_start')) {
                    contenter.css({"width":allWidth, "height":allHeight, "top":"0px", "left":"0px"});
                }

            });


            //���������ȥ��ʱ��
            if (top.topMaximize) {
                topMaximize('top', header);
            }
            ;
            if (left.leftMaximize) {
                topMaximize('left', leftBar);
            }
            ;
            if (right.rightMaximize) {
                topMaximize('right', rightBar);
            }
            ;
            if (bottom.bottomMaximize) {
                topMaximize('bottom', footer);
            }
            ;
            if (center.cneterMaximize) {
                topMaximize('center', contenter);
            }
            ;

            function topMaximize(type, obj) {
                var maxDiv = $('<a class="max_div" href="javascript:void(0)" onclick="javascript:void(0)"></a>');
                obj.append(maxDiv);
                obj.hover(function () {
                    maxDiv.show().animate({opacity:1}, 'fast');
                }, function () {
                    maxDiv.animate({opacity:0}, 'fast', function () {
                        $(this).hide()
                    });
                });
                if (type == 'top') {
                    maxDiv.bind('click', function () {
                        maxStart(type)
                    });
                }
                ;
                if (type == 'left') {
                    maxDiv.bind('click', function () {
                        maxStart(type)
                    });
                }
                ;
                if (type == 'right') {
                    maxDiv.bind('click', function () {
                        maxStart(type)
                    });
                }
                ;
                if (type == 'bottom') {
                    maxDiv.bind('click', function () {
                        maxStart(type)
                    });
                }
                ;
                if (type == 'center') {
                    maxDiv.bind('click', function () {
                        maxStart(type)
                    });
                }
                ;

            }

            ;

            function maxStart(type) {
                if (type == 'center') {
                    var obj = contenter;
                    if (obj.hasClass('max_start')) {
                        var centerOldW = thisBox.width() - rightBar.width() - leftBar.width();
                        var centerOldH = thisBox.height() - header.height() - footer.height();
                        obj.removeClass('max_start').animate({width:centerOldW, height:centerOldH, top:centerOldT, left:centerOldL}, 'fast', function () {
                            $(this).css('z-index', 1)
                        });
                    } else {
                        centerOldW = obj.width();
                        centerOldH = obj.height();
                        centerOldT = obj.css('top');
                        centerOldL = obj.css('left');
                        obj.addClass('max_start').css({'z-index':2}).animate({width:allWidth, height:allHeight, top:0, left:0}, 'fast');
                    }
                    ;
                } else if (type == 'top') {
                    var obj = header;
                    if (obj.hasClass('max_start')) {
                        temBodyWidth = thisBox.width();
                        if (temBodyWidth > topOldW) {
                            topOldW = temBodyWidth;
                        }
                        //��ԭ
                        obj.removeClass('max_start').animate({width:topOldW, height:topOldH, top:topOldT}, 'fast', function () {
                            $(this).css('z-index', 1)
                        });
                        $('.wrap_div', obj).next().show();
                    } else {
                        //���
                        topOldW = obj.width();
                        topOldH = obj.height();
                        topOldT = obj.css('top');
                        obj.addClass('max_start').css({'z-index':2}).animate({width:allWidth, height:allHeight, top:0}, 'fast');
                        $('.wrap_div', obj).next().hide();
                    }
                    ;
                } else if (type == 'left') {
                    var obj = leftBar;
                    if (obj.hasClass('max_start')) {
                        var temLeftH = thisBox.height() - header.height() - footer.height();
                        if (temLeftH > leftOldH) {
                            leftOldH = temLeftH;
                        }
                        obj.removeClass('max_start').animate({width:leftOldW, height:leftOldH, top:leftOldT}, 'fast', function () {
                            $(this).css('z-index', 1)
                        });
                        $('.wrap_div', obj).next().show();
                    } else {
                        leftOldW = obj.width();
                        leftOldH = obj.height();
                        leftOldT = obj.css('top');
                        obj.addClass('max_start').css({'z-index':2}).animate({width:allWidth, height:allHeight, top:0}, 'fast');
                        $('.wrap_div', obj).next().hide();
                    }
                    ;
                } else if (type == 'right') {
                    var obj = rightBar;
                    if (obj.hasClass('max_start')) {
                        //��ԭ
                        var temRightH = thisBox.height() - header.height() - footer.height();
                        if (temRightH > rightOldH) {
                            rightOldH = temRightH;
                        }
                        obj.removeClass('max_start').animate({width:rightOldW, height:rightOldH, top:rightOldT}, 'fast', function () {
                            $(this).css('z-index', 1)
                        });
                        $('.wrap_div', obj).next().show();
                    } else {
                        //���
                        rightOldW = obj.width();
                        rightOldH = obj.height();
                        rightOldT = obj.css('top');
                        obj.addClass('max_start').css({'z-index':2}).animate({width:allWidth, height:allHeight, top:0}, 'fast');
                        $('.wrap_div', obj).next().hide();
                    }
                    ;
                } else if (type == 'bottom') {
                    var obj = footer;
                    if (obj.hasClass('max_start')) {
                        var bottomOldT = header.height() + contenter.height();
                        var tembotW = thisBox.width();
                        if (tembotW > bottomOldW) {
                            bottomOldW = tembotW;
                        }
                        obj.removeClass('max_start').animate({width:bottomOldW, height:bottomOldH, top:bottomOldT}, 'fast', function () {
                            $(this).css('z-index', 1)
                        });
                        $('.wrap_div', obj).next().show();
                    } else {
                        bottomOldW = obj.width();
                        bottomOldH = obj.height();
                        bottomOldT = obj.css('top');
                        obj.addClass('max_start').css({'z-index':2}).animate({width:allWidth, height:allHeight, top:0}, 'fast');
                        $('.wrap_div', obj).next().hide();
                    }
                    ;
                }
                ;
            }


            topRevise.click(function () {
                onOff($(this));
            });
            bottomRevise.click(function () {
                onOff($(this));
            });
            leftRevise.click(function () {
                onOff($(this));
            });
            rightRevise.click(function () {
                onOff($(this));
            });

            //��ʾ�������жϷ�����ͨ���ж�a��ǩ��id��ֵ��ȷ��ִ���ĸ�������
            function onOff(obj) {
                if ($(obj).attr('id') == 'top_resizer') {//top����
                    topOnOff();
                } else if ($(obj).attr('id') == 'bottom_resizer') {//bottom����
                    bottomOnOff();
                } else if ($(obj).attr('id') == 'left_resizer') {//left����
                    leftOnOff();
                } else if ($(obj).attr('id') == 'right_resizer') {//right����
                    rightOnOff();
                }
                ;
            }

            ;

            //top����/��ʾ
            function topOnOff() {
                displayDiv = header.find('.wrap_div');
                displayDiv.toggle();
                if (topRevise.hasClass('top_down')) {
                    topHeight = top.topHeight;
                    forPane();
                    topRevise.removeClass('top_down');
                    topReviseAll.css({'top':topReviseTop}).removeClass('unbind').bind('mousedown', draggable);
                } else {
                    topHeight = 6;
                    forPane();
                    topRevise.addClass('top_down');
                    topReviseAll.css({'top':0}).addClass('unbind').unbind('mousedown');
                }
                ;
            }

            ;
            function bottomOnOff() {
                displayDiv = footer.find('.wrap_div');
                displayDiv.toggle();
                if (bottomRevise.hasClass('bottom_down')) {
                    bottomHeight = bottom.bottomHeight;
                    forPane();
                    bottomRevise.removeClass('bottom_down');
                    bottomReviseAll.removeClass('unbind').bind('mousedown', draggable);
                } else {
                    bottomHeight = 6;
                    forPane();
                    bottomRevise.addClass('bottom_down');
                    bottomReviseAll.addClass('unbind').unbind('mousedown');
                }
                ;
            }

            ;
            function leftOnOff() {
                displayDiv = leftBar.find('.wrap_div');
                displayDiv.toggle();
                if (leftRevise.hasClass('left_open')) {
                    leftWidth = left.leftWidth;
                    forPane();
                    leftRevise.removeClass('left_open');
                    leftReviseAll.css({'left':leftReviseL}).removeClass('unbind').bind('mousedown', draggable);
                } else {
                    leftWidth = 6;
                    forPane();
                    leftRevise.addClass('left_open');
                    leftReviseAll.css({'left':1}).addClass('unbind').unbind('mousedown');
                }
                ;
            }

            ;
            function rightOnOff() {
                displayDiv = rightBar.find('.wrap_div');
                displayDiv.toggle();
                if (rightRevise.hasClass('right_open')) {
                    rightWidth = right.rightWidth;
                    forPane();
                    rightRevise.removeClass('right_open');
                    rightReviseAll.removeClass('unbind').bind('mousedown', draggable);
                } else {
                    rightWidth = 6;
                    forPane();
                    rightRevise.addClass('right_open');
                    rightReviseAll.addClass('unbind').unbind('mousedown');
                }
                ;
            }

            ;


            if (top.topSwitch == true) {
                topReviseAll.bind('mousedown', draggable);
            } else {
                topReviseAll.css({"cursor":"default"});
            }
            if (left.leftSwitch == true) {
                leftReviseAll.bind('mousedown', draggable);
            } else {
                leftReviseAll.css({"cursor":"default"});
            }
            if (right.rightSwitch == true) {
                rightReviseAll.bind('mousedown', draggable);
            } else {
                rightReviseAll.css({"cursor":"default"});
            }
            if (bottom.bottomSwitch == true) {
                bottomReviseAll.bind('mousedown', draggable);
            } else {
                bottomReviseAll.css({"cursor":"default"});
            }


            jQuery.extend({
                layout:{
                    topOnOff:function () {//�ֶ���ʾ/����top��bottom��left��right
                        topOnOff();
                    },
                    bottomOnOff:function () {
                        bottomOnOff();
                    },
                    leftOnOff:function () {
                        leftOnOff();
                    },
                    rightOnOff:function () {
                        rightOnOff();
                    },
                    allOnOff:function () {
                        allOnOff();
                    },
                    setSize:function (opts) {//�ֶ��ı�top��bottom��left��right�߿�
                        var defaults = {
                            left:0,
                            right:0,
                            top:0,
                            bottom:0
                        };
                        $.extend(defaults, opts);
                        var oldTopHeight = 0;
                        var oldLeftWidth = 0;
                        var oldRightWidth = 0;
                        var oldBottomHeight = 0;
                        if (defaults.left != 0 && defaults.right != 0 && defaults.top != 0 && defaults.bottom != 0) {
                            oldLeftWidth = leftWidth;
                            oldTopHeight = topHeight;
                            oldRightWidth = rightWidth;
                            oldBottomHeight = bottomHeight;
                            leftWidth = defaults.left;
                            rightWidth = defaults.right;
                            topHeight = defaults.top;
                            bottomHeight = defaults.bottom;
                            if (oldLeftWidth == leftWidth && oldRightWidth == rightWidth && oldTopHeight == topHeight && oldBottomHeight == bottomHeight) {
                                leftWidth = left.leftWidth;
                                rightWidth = right.rightWidth;
                                topHeight = top.topHeight;
                                bottomHeight = bottom.bottomHeight;
                            }
                            ;
                            forPane();
                        } else if (defaults.left != 0) {
                            oldLeftWidth = leftWidth;
                            leftWidth = defaults.left;
                            if (oldLeftWidth == leftWidth) {
                                leftWidth = left.leftWidth;
                            }
                            ;
                            forPane();
                        } else if (defaults.right != 0) {
                            oldRightWidth = rightWidth;
                            rightWidth = defaults.right;
                            if (oldRightWidth == rightWidth) {
                                rightWidth = right.rightWidth;
                            }
                            ;
                            forPane();
                        } else if (defaults.top != 0) {
                            oldTopHeight = topHeight;
                            topHeight = defaults.top;
                            if (oldTopHeight == topHeight) {
                                topHeight = top.topHeight;
                            }
                            ;
                            forPane();
                        } else if (defaults.bottom != 0) {
                            oldBottomHeight = bottomHeight;
                            bottomHeight = defaults.bottom;
                            if (oldBottomHeight == bottomHeight) {
                                bottomHeight = bottom.bottomHeight;
                            }
                            ;
                            forPane();
                        }
                        ;
                    },

                    dragSwitch:function (opts) {//�ֶ�����top��bottom��left��right��ק
                        var defaults = {
                            allDrag:false,
                            leftDrag:false,
                            rightDrag:false,
                            topDrag:false,
                            bottomDrag:false
                        };
                        $.extend(defaults, opts);
                        if (defaults.allDrag == true) {
                            if (topReviseAll.hasClass('unbind') || rightReviseAll.hasClass('unbind') || bottomReviseAll.hasClass('unbind') || leftReviseAll.hasClass('unbind')) {
                                topReviseAll.removeClass('unbind').bind('mousedown', draggable);
                                bottomReviseAll.removeClass('unbind').bind('mousedown', draggable);
                                leftReviseAll.removeClass('unbind').bind('mousedown', draggable);
                                rightReviseAll.removeClass('unbind').bind('mousedown', draggable);
                            } else {
                                topReviseAll.addClass('unbind').unbind('mousedown');
                                bottomReviseAll.addClass('unbind').unbind('mousedown');
                                leftReviseAll.addClass('unbind').unbind('mousedown');
                                rightReviseAll.addClass('unbind').unbind('mousedown');
                            }
                            ;
                        } else if (defaults.topDrag == true) {
                            if (topReviseAll.hasClass('unbind')) {
                                topReviseAll.removeClass('unbind').bind('mousedown', draggable);
                            } else {
                                topReviseAll.addClass('unbind').unbind('mousedown');
                            }
                            ;
                        } else if (defaults.bottomDrag == true) {
                            if (bottomReviseAll.hasClass('unbind')) {
                                bottomReviseAll.removeClass('unbind').bind('mousedown', draggable);
                            } else {
                                bottomReviseAll.addClass('unbind').unbind('mousedown');
                            }
                            ;
                        } else if (defaults.leftDrag == true) {
                            if (leftReviseAll.hasClass('unbind')) {
                                leftReviseAll.removeClass('unbind').bind('mousedown', draggable);
                            } else {
                                leftReviseAll.addClass('unbind').unbind('mousedown');
                            }
                            ;
                        } else if (defaults.rightDrag == true) {
                            if (rightReviseAll.hasClass('unbind')) {
                                rightReviseAll.removeClass('unbind').bind('mousedown', draggable);
                            } else {
                                rightReviseAll.addClass('unbind').unbind('mousedown');
                            }
                            ;
                        }
                    },
                    windowMax:function (opts) {//��󻯴��ڵ���
                        var defaults = {
                            topMax:false,
                            leftMax:false,
                            rightMax:false,
                            bottomMax:false,
                            centerMax:false
                        };
                        $.extend(defaults, opts);
                        if (defaults.topMax == true) {
                            maxStart('top');
                        } else if (defaults.leftMax == true) {
                            maxStart('left');
                        } else if (defaults.rightMax == true) {
                            maxStart('right');
                        } else if (defaults.bottomMax == true) {
                            maxStart('bottom');
                        } else if (defaults.centerMax == true) {
                            maxStart('center');
                        }
                    }
                }
            });


        }
    })
})(jQuery);
