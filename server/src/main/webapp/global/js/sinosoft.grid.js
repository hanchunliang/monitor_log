/*!
 * grid(精简版)
 * 规定，grid的第一页的pageNo为1
 * levone
 * Mail:zuoliwen@sinosoft.com.cn
 * Date: 2012-07-03
 */
//查询keyCode keyUp
//1171
(function ($) {
    $.fn.extend({
        Grid: function(opts){
            var defaults = {
                url       : "grid.json" ,
                dataType  : "json" ,
                height    : "auto" ,
                width     : "auto",
                loadText  : "数据加载中…",
                colums    : "",
                rowNum    : "",
                number    : true,
                isAsync : true,
                hasSpan   : false,
                //queryData : {"age":1},
                multiselect: true,
                hasAllCheckbox:true,//如果为true，有全选的checkbox；如果不为true的haunted，没有全选的checkbox
                autoColWidth: true,
                radioSelect: false,
                sorts     : false,
                colDisplay: true,
                queryData : {}, //用于grid初始化查询数据时传参数用的
                frontJumpPage : true,
                draggable : true,
                sequence  : true,
                clickSelect :true,
                searchClass : "",
                searchBtn:"",
                isFrontSearch : false,//用来判断是前台查询还是后台查询的标志，如果为true则是前端查询，false则是后端查询
                //true为后端分页，before为前端分页
                afterRepage : true,
                //下面几个是新加的属性
                modifyUrl : "",
                isCanModify : false,
                operateObj : "",
                checkboxClickFun : "",
                pages     : {
                    pager    : true ,
                    renew    : false ,
                    paging   : true ,
                    goPage   : true ,
                    info     : true
                },
                callback : {
                    beforeDelData : function(gridNode){},
                    afterDelData: function(gridNode){return false;}
                }
            };
            var defaults = $.extend(defaults, opts);
            var g = $(this);
            var $gBox = $('<div class="grid_box"></div>').width('100%');

            var $gHeader = $('<div class="grid_head"></div>');
            var $gHeadBox = $('<div class="head_box"></div>');

            var $gHeadColumn = $('<div class="gird_head_column"></div>');

            var $needle = $('<div class="needle"></div>');

            var $gView = $('<div class="grid_view"></div>');
            var $load = $('<div class="loading"><span>' + defaults.loadText + '</span></div>');

            var colAttributes = {};
            colAttributes.widths = [];
            colAttributes.aligns = [];
            colAttributes.colors = [];
            colAttributes.name = [];
            colAttributes.pk = [];
            colAttributes.sorts = [];
            colAttributes.text = [];

            var widths = [],aligns = [],colors = [],allCh = [],hids = [];
            var $checkBox = $('<input type="checkbox" value="" name="all_sec" class="chex" />');
            var $gNumber = '';

            var adaptive = 0, str = 0, end = defaults.rowNum, totalPage = 0;

            var onOff,
                pageUnclik,
                disb = true,
                trId,
                oW,
                nW,
                tI,
                rows,
                total,
                pFirst,
                pPrev,
                pNext,
                pLast,
                pNub,
                pB = 0,
                pSel,
                ps,
                _stop;
            var _move=false;
            var _x;


            //判断列的name属性中，是否有叫id
            var col_name_is_id = false;
            //如果id是某一列的值，那么就记录这一列的序号
            var col_is_id_index = "";

            //初始化的时候data的值
            var originGridData = "";

            var _data;

            //用来判断pager是不是在grid第一次初始化的时候用的，如果是grid第一次初始化，他是true
            //在pager也初始化完了之后，我就让他编程false了
            var firstCreatePager = true;

            //这种情况是grid的json不通过ajax请求json数据，而是直接在配置里边写的grid的json数据
            if(defaults.girdData){
                _data = defaults.girdData;
                originGridData = _data;
            }

            g.append($gBox);

            var $gW = $gBox.width();
            var buttonLeft = 0;
            var $thisTr = "";

            autoWidth();

            createGridHead();

            //创建Grid的头部
            function createGridHead() {
                var col = defaults.colums;

                if(defaults.multiselect) {
                    if(defaults.hasAllCheckbox) {
                        $gHeadColumn.addClass("multiple").append($checkBox);
                    } else {
                        $gHeadColumn.addClass("multiple");
                    }
                    $gHeader.append($gHeadColumn);
                };

                if(defaults.number){
                    $gNumber = $('<div class="gird_head_column number"><span class="number">序号</span></div>');
                    $gHeader.append($gNumber);
                };

                for(var i = 0; i < col.length; i++){
                    if(!defaults.sorts && !defaults.colDisplay) {
                        $gHeadColumn = $('<div class="gird_head_column cols col_' + i +'"></div>');
                    }else{
                        $gHeadColumn = $('<div class="gird_head_column cols col_' + i +'"><div class="grid_menu"></div></div>');
                    };
                    $gHeadColumn.append("<span>"+ col[i].text +"</span>");
                    if(col[i].id){
                        $gHeadColumn.attr("id", col[i].id);
                    }else{
                        alert("id不能为空");
                        return false;
                    };
                    if(col[i].name){
                        $gHeadColumn.attr("name", col[i].name);
                        colAttributes.name.push(col[i].name);
                    }else{
                        alert("name不能为空");
                        return false;
                    };
                    if(col[i].pk) {
                        colAttributes.pk.push(col[i].pk);
                    } else {
                        colAttributes.pk.push(false);
                    }
                    if(col[i].text) {
                        colAttributes.text.push(col[i].text);
                    } else {
                        colAttributes.text.push("");
                    }
                    if(col[i].sort) {
                        colAttributes.sorts.push(col[i].sort);
                    } else {
                        colAttributes.sorts.push(false);
                    }
                    if(col[i].index){
                        $gHeadColumn.attr("index",col[i].index);
                    };

                    if(col[i].width){
                        $gHeadColumn.css({"width":col[i].width});
                        colAttributes.widths.push(col[i].width);
                    }else{
                        $gHeadColumn.css({"width":adaptive});
                        colAttributes.widths.push(adaptive);
                    };

                    if(col[i].align){
                        colAttributes.aligns.push(col[i].align);
                    }else{
                        colAttributes.aligns.push("left");
                    };

                    if(col[i].color){
                        colAttributes.colors.push(col[i].color);
                    }else{
                        colAttributes.colors.push("inherit");
                    };

                    if(defaults.pages.goPage || defaults.pages.paging){
                        formatHids(i);
                    };
                    $gHeader.append($gHeadColumn);

                    $gHeadColumn
                        .bind({"mouseover":changeNeedle,"mouseout":unHover})
                        .children("div").bind("click", colMenuOpt);
                    if(defaults.sequence){
                        $gHeadColumn.bind("click", colSequence);
                    }
                    if(defaults.draggable){
                        $gHeadColumn.bind("mousedown",moveThead);
                    };
                };
                $gHeader.append($needle);
                $gBox.append($gHeader);
                if(defaults.sorts || defaults.colDisplay){
                    greatMenu();
                    $needle.bind("mousedown", dragCol);
                };
                $("div.gird_head_column",$gHeader).wrapAll($gHeadBox);
            };

            //这个方法好像是为了列隐藏用的？？？？？？
            function formatHids(i){
                var obj = new HideInfo('col_' + i, false);
                hids.push(obj);
            };

            function HideInfo(name,isHide){
                this.name = name;
                this.isHide = isHide;
                this.getInfo = function(){
                    return this.name + ": " + this.isHide;
                };
            };

            //添加grid的列菜单
            function colMenuOpt(e){
                var $gM = $gBox.children('div.grid_head_menu');

                $('div.grid_head_menu', $gBox).hide();

                if($.browser.msie && ($.browser.version == "7.0")){
                    $('div.grid_head_menu:last',$gBox).css('border-width','0');
                }

                var offset = $(this).offset();
                var nH = $(this).height();
                var pHead = $(this).parent();

                var sm = $gW + g.offset().left - offset.left < $gM.width();

                pHead
                    .siblings().removeClass('select')
                    .children('div.grid_menu').removeClass('clk');
                $(this).addClass('clk');
                pHead.addClass('select');

                if(sm){
                    $gM.css({'left':offset.left - g.offset().left - $gM.width() + $(this).width(),'top':nH}).show();

                    onOff = false;
                }else{
                    $gM.css({'left':offset.left - g.offset().left,'top':nH}).show();
                    onOff = true;
                };

                e.stopPropagation();
                var part = $('div.grid_head_menu > ul > li.parting',$gBox);

                part.bind('mouseover',{part:part,onOff:onOff},subMopt);
            };

            //列菜单弹出子菜单的方法
            function subMopt(e){
                if($.browser.msie&&($.browser.version == "7.0")){
                    $('div.grid_head_menu:last',$gBox).css('border-width','1px');
                };
                var part = e.data.part;
                var onOff = e.data.onOff;
                var sM = $('div.grid_head_menu > div.grid_head_menu',$gBox);
                if(onOff){
                    sM.css({'top':part.position().top,'left':part.position().left + 126}).show();
                }else{
                    sM.css({'top':part.position().top,'left':-128}).show();
                };
                $('li',sM).unbind('click');
                $('li',sM).bind('click',subClick);
                return false;
            };

            //点击列菜单弹中的条目时所要执行的方法
            function subClick(e){
                e.stopPropagation();
                var check = $('div > label',$(this));
                var index = $(this).index();
                var head = $("div.col_" + index,$gHeader);
                var hI = head.index();
                var bw = $("div.gird_head_column[col]:visible",$gHeader).length;
                var tb = $("table",$gView);
                var tr = $("table > tbody > tr",$gView);
                var ul = check.parents('ul');
                var oI = hI;
                if(defaults.multiselect){
                    oI = oI - 1;
                };
                if(defaults.number){
                    oI = oI - 1;
                };
                var obj = hids[oI];
                if(check.hasClass('chected')){
                    if(disb){
                        var tw = tb.width() - head.width();
                        check.removeClass('chected');
                        head.hide();
                        tb.width(tw - bw);
                        tr.each(function(){
                            $("td",$(this)).eq(hI).hide();
                        });
                        obj.isHide=true;
                    }
                }else{
                    var tw = tb.width() + head.width();
                    check.addClass('chected');
                    $('label.disabled',ul).removeClass('disabled');
                    head.show();
                    tb.width(tw);
                    tr.each(function(){
                        $("td",$(this)).eq(hI).show();
                    });
                    disb = true;
                    obj.isHide=false;
                };
                var cl = $("label.chected",ul).length;
                if(cl == 1){
                    var last = $('label.chected',ul);
                    last.addClass('disabled');
                    disb = false;
                };
            }

            $gHeader.width($gW);

            if(defaults.height != 'auto'){
                $gView.height(defaults.height - $gHeader.height() - 1);
            };

            if(defaults.width == 'auto'){
                $gBox.width($gW);
            }else{
                $gBox.width(defaults.width);
            };

            //这一步执行完，DOM文档中已经有$gView这个div了
            $gBox.append($gView);

            if( _data ) {
                loading();
                initGrid(_data, str, end, colAttributes);
                _stop = _data.rows.length;
            } else {
                createData();
            }

            $checkBox.bind("click",allClick);

            $gView.scroll(function(){
                var sL = $gView.scrollLeft();
                $gHeader.css('margin-left',-sL);
            });

            //制作page,这个的执行和createData只能有一个执行，因为firestCreatePager的存在，所以在初始化的时候不会引起pager
            //生成两次的冲突。
            //写两次的目的就是为了解决ajax异步加载数据与配置项中直接写数据（或ajax同步加载数据）_data有时没有的错误
            if( _data != undefined && defaults.pager && firstCreatePager){
                firstCreatePager = false;
                greadPage();
            }

            $gView.width($gW);//.height($gView.height());

            //创建grid 中间的内容  的  方法
            function createData(createCondition) {
                var obj = {};
                var url = "";
                var dataType = "";
                var type = "";
                //我想让这个createData方法复用，就是查询，刷新什么的都用这个方法
                //所以传了个createCondition条件，如果这个条件为undefined，那么就是页面刚开始加载的时候，
                //grid初始化
                if(!createCondition) {
                    url = defaults.url;
                    dataType = defaults.dataType;
                    type = defaults.type;
                    obj.pageNo = pB;
                    obj.rowNum = defaults.rowNum;
                    obj = $.extend(defaults.queryData,obj);
                } else {
                    alert("非页面初始化的时候，grid的初始化！");
                }
                $.ajax({
                    url: url,
                    dataType : dataType,
                    async : defaults.isAsync,
                    type : type,
                    data: obj,
                    //async : true,
                    //processData : false,
                    beforeSend : function(){loading();},
                    error : function (XMLHttpRequest, errorThrown) {
                        alert("数据加载出错！" + errorThrown);
                    },
                    success: function(returnData){
                        if(returnData == null || returnData == undefined || returnData.total == 0 || returnData.rows.length == 0) {
                            returnData = '{"total":"0", "rows":[]}';
                            returnData = eval("(" + returnData + ")");
                        };
                        //判断pk的标志
                        var col_pk_flag = true;
                        //判断colums中的id,如果colums中有个name的值为id，那么必须这是pk属性为true
                        for(var i = 0; i < colAttributes.name.length; i++) {
                            if(colAttributes.name[i] == "id") {
                                col_name_is_id = true;
                                col_is_id_index = i;
                                if(colAttributes.pk[i] != true) {
                                    col_pk_flag = false;
                                    break;
                                }
                            }
                        };
                        if(!col_pk_flag) {
                            alert("grid初始化失败，列中name的值不能为id，除非设置该列的pk属性为true!");
                            return false;
                        }
                        initGrid(returnData, str, end, colAttributes);
                        _data = returnData;
                        //制作page
                        if(defaults.pager && _data != undefined && firstCreatePager){
                            firstCreatePager = false;
                            greadPage();
                        }
                        originGridData = _data;
                        _stop = _data.rows.length;
                    }
                });
            };

            function initGrid(data, startParam, endParam, colParams) {
                readJson(data, startParam, endParam, colParams);
                $load.hide();
                $('tr',$gView).hover(function(){
                    $(this).addClass('hover');
                },function(){
                    $(this).removeClass('hover');
                });
                //给tr加上点击这一行，该行对应的checkbox状态改变的事件
                $('tr',$gView).bind('click', trClick);
            };

            function readJson(data, startParam, endParam, colParams){
                var gTable = '<table border="0" cellspacing="0" cellpadding="0">';
                var rows = data.rows;
                var total = data.total;
                //计算这个grid一共有多少页
                if(defaults.rowNum != "") {
                    if(data.total)
                        totalPage = Math.ceil(data.total/defaults.rowNum);
                }
                if(rows == 0 || total == 0) {
                    gTable  = gTable + '<div style="text-align:center;">暂无数据......</div>';
                }
                var colLen = defaults.colums.length;
                var allW = 0;
                gTable = gTable + '<tr class="th_rows">';
                if(defaults.multiselect) {
                    gTable = gTable + '<td style="width:22px">&nbsp;</td>';
                    allW += 22;
                };
                if(defaults.number) {
                    gTable = gTable + '<td style="width:30px">&nbsp;</td>';
                    allW += 30;
                }
                for(i = 0; i < colLen; i++){
                    gTable  = gTable + '<td col="col_' + i + '" style="width:' + colParams.widths[i] + 'px">&nbsp;</td>';
                    allW += Math.abs(colParams.widths[i]);
                }
                gTable  = gTable + '</tr>';
                //加载数据的这个startParam和endParam要分前端和后端，后端的就是startParam为0，endParam初始化为页面的rowNum，
                //然后判断传过来的数据的长度和rowNum的大小比较，如小于rowNum，那么endParam为传过来的数据长度。
                //如果是前端分页，那么startParam和endParam就不是固定的了，要随着pageNo的变化来改变
                if(defaults.pager) {
                    if(defaults.afterRepage) {
                        //后端分页(要考虑无数据的时候，或者没有total的时候)
                        startParam = 0;
                        endParam = defaults.rowNum;
                        if( (pB+1) == totalPage) {
                            //解释下这个if判断的作用，首先endParam是本来grid计划应该加载的数据结束位置，是个数字
                            //如果得到的数据的长度小于endParam这个结束标志数字，那么说明这是到了最后一页了
                            endParam = rows.length;
                            pageUnclik = true;
                        };
                    } else {
                        //前端分页（要考虑无数据的时候，或者没有total的时候）
                        startParam = pB * defaults.rowNum;
                        endParam = (pB + 1) * defaults.rowNum;
                        //alert(rows.length);
                        if(rows.length <= endParam) {
                            //解释下这个if判断的作用，首先endParam是本来grid计划应该加载的数据结束位置，是个数字。
                            //如果得到的数据的长度小于endParam这个结束标志数字，那么说明这是到了最后一页了
                            endParam = rows.length;
                            pageUnclik = true;
                        };
                    }
                } else {
                    startParam = 0;
                    endParam = data.rows.length;
                }
                if(data.rows.length == 0) {
                    startParam = 0;
                    endParam = 0;
                    pageUnclik = true;
                }
//				为什么要加底下的这个if，没弄明白？？？？？？？	
//                if(!defaults.pager) {
//                	endParam = data.total;
//                }
                for(var j = startParam; j < endParam; j++){
                    var cell = rows[j].cell;
                    //判断id是否是某一列的值
                    if(col_name_is_id) {
                        gTable = gTable + '<tr id="row_' + rows[j].cell[col_is_id_index] + '">';
                    } else {
                        gTable = gTable + '<tr id="row_' + rows[j].id + '">';
                    }
                    allCh.push(false);
                    if(defaults.multiselect){
                        if(rows[j].cell[colLen] == "true") {
                            gTable  = gTable + '<td class="multiple tr_multiple"><input name="g_check" type="checkbox" checked="checked" value="" /></td>';
                        } else {
                            gTable  = gTable + '<td class="multiple tr_multiple"><input name="g_check" type="checkbox" value="" /></td>';
                        }
                    };
                    if(defaults.number) {
                        var tempIndex = 0;
                        if(defaults.frontJumpPage) {
                            tempIndex = j + 1;
                        } else {
                            tempIndex = j + 1 + str;
                        }
                        gTable = gTable + '<td class="number"><span title="' + tempIndex + '">' + tempIndex + '</span></td>' ;
                    };
                    //用来记住操作列的名字的变量
                    var operator_col_name = "";
                    //用来保存操作列所有的内容的变量
                    var allOperateInfo = "";
                    if(defaults.operateObj != "") {
                        for(var x = 0; x < defaults.operateObj.length; x++) {
                            //首先把需要合并的弄到一起，主要就是操作列，我们需要把他们弄到一起
                            if(defaults.operateObj[x].text != "" && defaults.operateObj[x].text != undefined) {
                                operator_col_name = defaults.operateObj[x].name;
                                allOperateInfo = allOperateInfo + "<a class='td_href'>" + defaults.operateObj[x].text +"</a>";
                            }
                        }
                    }
                    for(var i = 0; i < colLen; i++){
                        var text = cell[i];
                        if(i + 1 == colLen) {
                            //最后一列是否需要超链接的标志，他肯定不是操作列
                            var lastColIsHref = false;
                            //判断有没有操作列
                            if(operator_col_name != "") {
                                gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i] + ';padding:0 3px;color:'
                                    + colParams.colors[i] + '">' + allOperateInfo + '</td>';
                            } else {
                                for(var c = 0; c < defaults.operateObj.length; c++) {
                                    //判断最后一列是否需要超链接
                                    if(defaults.operateObj[c].name == colParams.name[i] && (defaults.operateObj[c].text == "" || defaults.operateObj[c].text == undefined)) {
                                        //最后一列需要超链接
                                        lastColIsHref = true;
                                        if(text) {
                                            gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i]
                                                + ';padding:0 3px;color:' + colParams.colors[i] + '"><a class="td_href">' + text + '</a></td>';
                                        } else {
                                            gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i]
                                                + ';padding:0 3px;color:' + colParams.colors[i] + '">&nbsp;</td>';
                                        }
                                    }
                                }
                                //说明最后一列既不是操作列又不需要超链接
                                if(!lastColIsHref) {
                                    if(text) {
                                        gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i]
                                            + ';padding:0 3px;color:' + colParams.colors[i] + '">' + text + '</td>';
                                    } else {
                                        gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i]
                                            + ';padding:0 3px;color:' + colParams.colors[i] + '">&nbsp;</td>';
                                    }
                                }
                            }
                        } else {
                            var thisColNeedHref = false;
                            //判断除最后一列是否要作为超链接
                            for(var a = 0; a < defaults.operateObj.length; a++) {
                                if(defaults.operateObj[a].name == colParams.name[i]) {
                                    //说明这列需要href
                                    thisColNeedHref = true;
                                    if(text) {
                                        gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i]
                                            + ';padding:0 3px;color:' + colParams.colors[i] + '"><a class="td_href">' + text + '</a></td>';
                                    } else {
                                        gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i]
                                            + ';padding:0 3px;color:' + colParams.colors[i] + '">&nbsp;</td>';
                                    }
                                }
                            }
                            if(!thisColNeedHref) {
                                if(text) {
                                    gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i]
                                        + ';padding:0 3px;color:' + colParams.colors[i] + '">' + text + '</td>';
                                } else {
                                    gTable  = gTable + '<td col="col_' + i + '" style="text-align:' + colParams.aligns[i]
                                        + ';padding:0 3px;color:' + colParams.colors[i] + '">&nbsp;</td>';
                                }
                            }
                        }
                    };
                    gTable  = gTable + '</tr>';
                };
                gTable  = gTable + '</table>';
                $gView.append(gTable);


                if(defaults.multiselect && defaults.number) {
                    //有checkbox的事件的加法
                    $("table", $gView).find("tr").each(function(i){
                        var row = {};
                        //一行一行tr的生成row和加入事件
                        if(col_name_is_id) {
                            //row的id是他写的pk
                            for(var b = 0; b < colParams.name.length; b++) {
                                row[colParams.name[b]] = $(this).find("td").eq(b+2).text();
                            }
                        } else {
                            //row的id不是他写的pk，而是tr的id
                            row.id = $(this).attr("id") != undefined ? $(this).attr("id").replace(/row_/, "") : "";
                            for(var b = 0; b < colParams.name.length; b++) {
                                row[colParams.name[b]] = $(this).find("td").eq(b+2).text();
                            }
                        }
                        //开始加入事件绑定
                        $(this).find("td").each(function(i){
                            if($(this).find("a").length > 0) {
                                //得到列名
                                var thisColName = colParams.name[i - 2];
                                if(thisColName == operator_col_name) {
                                    //说明这是操作列
                                    $(this).find("a").each(function(){
                                        var thisAName = $(this).text();
                                        for(var n = 0; n < defaults.operateObj.length; n++) {
                                            var temN = n;
                                            function triggerClick(event) {
                                                defaults.operateObj[event.data.funIndex].operateFun(row);
                                            }
                                            if(defaults.operateObj[n].text == thisAName) {
                                                $(this).bind("click",{funIndex:n}, triggerClick);
                                            }
                                        }
                                    });
                                } else {
                                    for(var n = 0; n < defaults.operateObj.length; n++) {
                                        function triggerClick(event) {
                                            defaults.operateObj[event.data.funIndex].operateFun(row);
                                        }
                                        if(defaults.operateObj[n].name == thisColName) {
                                            $(this).find("a").eq(0).bind("click", {funIndex:n}, triggerClick);
                                        }
                                    }
                                }
                            }
                        });
                    });
                } else if((defaults.number && !defaults.multiselect) || (defaults.multiselect && defaults.number)) {
                    //没有checkbox的事件的加法
                    $("table", $gView).find("tr").each(function(i){
                        var row = {};
                        //一行一行tr的生成row和加入事件
                        if(col_name_is_id) {
                            //row的id是他写的pk
                            for(var m = 0; m < colParams.name.length; m++) {
                                row[colParams.name[m]] = $(this).find("td").eq(m+1).text();
                            }
                        } else {
                            //row的id不是他写的pk，而是tr的id
                            row.id = $(this).attr("id") != undefined ? $(this).attr("id").replace(/row_/, "") : "";
                            for(var m = 0; m < colParams.name.length; m++) {
                                row[colParams.name[m]] = $(this).find("td").eq(m+1).text();
                            }
                        }
                        $(this).find("td").each(function(i){
                            if($(this).find("a").length > 0) {
                                //得到列名
                                var thisColName = colParams.name[i - 2];
                                if(thisColName == operator_col_name) {
                                    //说明这是操作列
                                    $(this).find("a").each(function(){
                                        var thisAName = $(this).text();
                                        for(var n = 0; n < defaults.operateObj.length; n++) {
                                            function triggerClick(event) {
                                                defaults.operateObj[event.data.funIndex].operateFun(row);
                                            }
                                            if(defaults.operateObj[n].text == thisAName) {
                                                $(this).bind("click", {funIndex:n}, triggerClick);
                                            }
                                        }
                                    });
                                } else {
                                    for(var n = 0; n < defaults.operateObj.length; n++) {
                                        function triggerClick(event) {
                                            defaults.operateObj[event.data.funIndex].operateFun(row);
                                        }
                                        if(defaults.operateObj[n].name == thisColName) {
                                            $(this).find("a").eq(0).bind("click", {funIndex:n}, triggerClick);
                                        }
                                    }
                                }
                            }
                        });
                    });
                } else {
                    //没有checkbox的事件的加法
                    $("table", $gView).find("tr").each(function(i){
                        var row = {};
                        //一行一行tr的生成row和加入事件
                        if(col_name_is_id) {
                            //row的id是他写的pk
                            for(var m = 0; m < colParams.name.length; m++) {
                                row[colParams.name[m]] = $(this).find("td").eq(m).text();
                            }
                        } else {
                            //row的id不是他写的pk，而是tr的id
                            row.id = $(this).attr("id") != undefined ? $(this).attr("id").replace(/row_/, "") : "";
                            for(var m = 0; m < colParams.name.length; m++) {
                                row[colParams.name[m]] = $(this).find("td").eq(m).text();
                            }
                        }
                        $(this).find("td").each(function(i){
                            if($(this).find("a").length > 0) {
                                //得到列名
                                var thisColName = colParams.name[i];
                                if(thisColName == operator_col_name) {
                                    //说明这是操作列
                                    $(this).find("a").each(function(){
                                        var thisAName = $(this).text();
                                        for(var n = 0; n < defaults.operateObj.length; n++) {
                                            function triggerClick(event) {
                                                defaults.operateObj[event.data.funIndex].operateFun(row);
                                            }
                                            if(defaults.operateObj[n].text == thisAName) {
                                                $(this).bind("click", {funIndex:n}, triggerClick);
                                            }
                                        }
                                    });
                                } else {
                                    for(var n = 0; n < defaults.operateObj.length; n++) {
                                        function triggerClick(event) {
                                            defaults.operateObj[event.data.funIndex].operateFun(row);
                                        }
                                        if(defaults.operateObj[n].name == thisColName) {
                                            $(this).find("a").eq(0).bind("click", {funIndex:n}, triggerClick);
                                        }
                                    }
                                }
                            }
                        });
                    });
                }

                $('tr:odd',$gView).addClass('odd');
                $('table',$gView).width(allW);
            };

            //grid的前端查询
            if(defaults.searchClass != "") {
                $("." + defaults.searchClass).bind("keyup",
                    function(e){
                        searchFun(e,$("." + defaults.searchClass).val());
                    });
            }
            if(defaults.searchBtn != "") {
                $("." + defaults.searchBtn).bind("click", {btn:"button"},
                    function(e){
                        searchFun(e,$("." + defaults.searchClass).val());
                    });
            }

            //grid前端查询用到的方法。
            function searchFun(e,searchVal) {
                if(e.keyCode == "13" || e.data) {
                    //var searchVal = $("." + defaults.searchClass).val();
                    if(searchVal == "") {
                        _data = originGridData;
                        $gView.html("");
                        if(_data.total < defaults.rowNum) {
                            initGrid(_data, 0, _data.total, colAttributes);
                        } else {
                            initGrid(_data, 0, defaults.rowNum, colAttributes);
                        }
                        g.find(".grid_page").eq(0).remove();
                        greadPage();
                    } else {
                        var allCount = 0; //用来记录查询之后一共有多少条数据
                        //原始json数据一共有多少条记录
                        var originDataLength = originGridData.rows.length;
                        var originCellLength = originGridData.rows[0];
                        if(originCellLength) {
                            originCellLength = originGridData.rows[0].cell.length;
                        } else {
                            originCellLength = 0;
                        }
                        //字符串转化成json
                        //originGridData
                        //循环这个json数据
                        var temStr = '"rows":[';

                        for(var i = 0; i < originGridData.rows.length; i++) {
                            var temCount = 0;
                            for(var j = 0; j < originGridData.rows[i].cell.length; j++) {
                                if( -1 == originGridData.rows[i].cell[j].indexOf(searchVal) ) {
                                    temCount++;
                                }
                            }
                            if( temCount != originCellLength ) {
                                //说明这条记录是需要的，需要把它拼进新的json串中
                                var tem = '{"id":"' + originGridData.rows[i].id + '","cell":[';
                                for(var m = 0; m < originCellLength; m++) {
                                    if( m + 1 < originCellLength ) {
                                        tem = tem +  '"'+ originGridData.rows[i].cell[m] +'",';
                                    } else {
                                        tem = tem + '"'+ originGridData.rows[i].cell[m] +'"]},';
                                    }
                                }
                                temStr = temStr + tem;
                                allCount++;
                            }
                        }
                        var lastDouHaoIndex = temStr.lastIndexOf(",");
                        temStr = temStr.substring(0, lastDouHaoIndex);
                        temStr = '{"total":"' + allCount + '",' + temStr + ']}';
                        //alert(temStr);
                        //var temStr = '{"total":"1", "rows": [{"id":"1","cell": ["test01","管理员角色1","2012-08-23","2012-08-30"]}]}';
                        _data = eval('(' + temStr + ')');
                        $gView.html("");
                        if(_data.total < defaults.rowNum) {
                            initGrid(_data, 0, _data.total, colAttributes);
                        } else {
                            initGrid(_data, 0, defaults.rowNum, colAttributes);
                        }
                        g.find(".grid_page").eq(0).remove();
                        greadPage();
                    }
                }

            }

            $gView.scroll(function(){
                var sL = $gView.scrollLeft();
                $gHeader.css('margin-left',-sL);
            });

            //生成最下面的页码行
            function greadPage() {
                var $gPage = $('<div class="grid_page"><div class="grid_page_box"></div></div>');
                var $pBox = $('div.grid_page_box', $gPage);
                var $p = $('<div class="prick"></div>');
                //ps是全局变量，表示grid一共有多少页。而pB是从0开始的
                ps = Math.ceil((_data.total ? _data.total : _data.rows.length)/defaults.rowNum);
                if(ps == 0) {
                    ps = 1;
                }
                if(defaults.pages.goPage) {
                    var goTo = '<div class="grid_entry"><select name="grid_pages">';
                    for(i = 1; i < ps + 1; i++){
                        goTo = goTo + '<option>' + i + '</option>';
                    };
                    goTo = goTo + '</select></div>';
                    $pBox.append(goTo,'<div class="prick"></div>');
                    pSel = $('select', $pBox);
                    pSel.bind('change', {b:'select'}, jampPage);
                };
                if(defaults.pages.paging){
                    var $pagings = $('<span><b class="grid_page_fist unclick"></b></span><span><b class="grid_page_prev unclick"></b></span><div class="prick"></div><div class="grid_note"><input name="" type="text" class="page_nub" value="1" />页 共 '+ ps +' 页</div><div class="prick"></div><span><b class="grid_page_next"></b></span><span><b class="grid_page_last"></b></span><div class="prick"></div>');
                    pFirst = $('b.grid_page_fist',$pagings);
                    pPrev = $('b.grid_page_prev',$pagings);
                    pNub = $('input.page_nub',$pagings);  //页面输入框
                    pNext = $('b.grid_page_next', $pagings); //跳转到表格的下一页
                    pLast = $('b.grid_page_last', $pagings); //跳转到表格的最后一页

                    if(pageUnclik){
                        pNext.addClass('unclick');
                        pLast.addClass('unclick');
                    };
                    $pBox.append($pagings);
                    pFirst.bind("click",{b:'first'},pageFn);
                    pPrev.bind("click",{b:'prev'},pageFn);
                    pNext.bind("click",{b:'next'},pageFn);
                    pLast.bind("click",{b:'last'},pageFn);

                    pNub.bind('keyup',{b:'nub'},jampPage);
                };
                if(defaults.pages.renew){
                    var $refresh = $('<span title="刷新"><b class="grid_refresh"></b></span>');
                    $pBox.append($refresh,$p);
                    $refresh.bind('click',pageRefresh);
                };
                if(defaults.pages.info){
                    var $info = $('<div class="grid_info">每页显示 '+ defaults.rowNum +' 条数据 - 共 '+ _data.total +' 条数据</div>');
                    $pBox.append($info);
                };
                $gBox.append($gPage);
            };

            function jampPage(e){
                var path = e.data.b,
                    old = pB,
                    on = 'unclick',
                    un = !$(this).hasClass(on),

                    lHas = pLast.hasClass(on),

                    fHas = pFirst.hasClass(on);
                if(path == 'nub'){
                    if(e.keyCode == 13){
                        var tempNum = pNub.val();
                        var ok = tempNum >= 1 && tempNum <= ps;
                        if(ok){
                            pB = tempNum - 1;
                        };
                        str = pB * defaults.rowNum;
                        end = (pB + 1) * defaults.rowNum;
                        if(ok){
                            if(tempNum == 1){
                                if(!fHas){
                                    pFirst.addClass(on);
                                    pPrev.addClass(on);
                                };
                                if(lHas){
                                    pNext.removeClass(on);
                                    pLast.removeClass(on);
                                };
                            }else if(tempNum == ps){
//                                if(end > _stop)
//                                    end = _stop;
                                if(!lHas){
                                    pNext.addClass(on);
                                    pLast.addClass(on);
                                };
                                if(fHas){
                                    pFirst.removeClass(on);
                                    pPrev.removeClass(on);
                                };
                            }else{
                                if(lHas || fHas){
                                    pNext.removeClass(on);
                                    pLast.removeClass(on);
                                    pFirst.removeClass(on);
                                    pPrev.removeClass(on);
                                };
                            };
                        }
                        $(this).blur();
                    }else{
                        return false;
                    };
                }else if(path == 'select'){
                    //pB代表你选择的select的值
                    pB = $(this).val() - 1;
                    if(pB == 0){
                        if(!fHas){
                            pFirst.addClass(on);
                            pPrev.addClass(on);
                        };
                        if(lHas){
                            pNext.removeClass(on);
                            pLast.removeClass(on);
                        };
                    }else if((pB+1) == ps){
//                        //进入这个if表示当前select选择的是最后一页
//                        if(end > _stop)
//                            end = _stop;
                        if(!lHas){
                            pNext.addClass(on);
                            pLast.addClass(on);
                        };
                        if(fHas){
                            pFirst.removeClass(on);
                            pPrev.removeClass(on);
                        };
                    }else{
                        if(lHas || fHas){
                            pNext.removeClass(on);
                            pLast.removeClass(on);
                            pFirst.removeClass(on);
                            pPrev.removeClass(on);
                        };
                    };
                    str = pB * defaults.rowNum;
                    end = (pB+1) * defaults.rowNum;
//                    if(end > _stop)
//                        end = _stop;
                };
                rePage();
            };

            function pageFn(e){
                var path = e.data.b,
                    on = 'unclick',
                    un = !$(this).hasClass(on),
                    lHas = pLast.hasClass(on),
                    fHas = pFirst.hasClass(on);
                if(path == 'first' && un){
                    if(!fHas){
                        pFirst.addClass(on);
                        pPrev.addClass(on);
                        pNext.removeClass(on);
                        pLast.removeClass(on);
                    };
                    pB = 0;
                    str = 0;
                    end = defaults.rowNum;
                }else if(path == 'prev' && un) {
                    if(lHas){
                        pNext.removeClass(on);
                        pLast.removeClass(on);
                    };
                    pB --;
                    if(pB == 0) {
                        pFirst.addClass(on);
                        pPrev.addClass(on);
                    }
//                    if(pB <= 0){
//                        pB = 0;
//                        pFirst.addClass(on);
//                        pPrev.addClass(on);
//                    };
                    str = pB * defaults.rowNum;
                    end = (pB + 1) * defaults.rowNum;
                }else if(path == 'next' && un){
                    if(!lHas){
                        pFirst.removeClass(on);
                        pPrev.removeClass(on);
                    };
                    pB ++;
                    if((pB + 1) == ps){
                        pNext.addClass(on);
                        pLast.addClass(on);
                    };
                    str = pB * defaults.rowNum;
                    end = (pB + 1) * defaults.rowNum;
                    if(end > _stop)
                        end = _stop;
                }else if(path == 'last' && un){
                    if(!lHas){
                        pNext.addClass(on);
                        pLast.addClass(on);
                        pFirst.removeClass(on);
                        pPrev.removeClass(on);
                    };
                    pB = ps - 1;
                    str = pB * defaults.rowNum;
                    end = (pB + 1) * defaults.rowNum;
                }else{
                    return false;
                };
                rePage();
            };

            function cancelCheck(){
                if($(".chex").attr("checked")) {
                    $(".chex").attr("checked", false);
                }
            }

            function rePage(){
                var obj = {};
                obj.pageNo = pB + 1;
                obj.rowNum = defaults.rowNum;
                obj = $.extend(defaults.data,obj);
                $('table', $gView).remove();
                loading();
                if(defaults.pages.paging)
                    pNub.val(pB + 1);
                if(defaults.pages.goPage)
                    pSel.val(pB + 1);
                if(defaults.afterRepage) {
                    createData();
                } else {
                    initGrid(_data, str, end, colAttributes);
                }

                //在这里调用改变顺序的方法
                $(".gird_head_column").each(function(){
                    if($(this).find("span").length > 0) {
                        //查看span中是否有up或者down的class
                        var $thisSpan = $(this).find("span").eq(0);
                        if($thisSpan.hasClass("down")) {
                            //"click", {b:'up'} ,colSequence
                            var myE = {};
                            var myData = {};
                            myE.data = myData;
                            myE.data.b = "down";
                            myE.data.funUse = true;
                            colSequence(myE, $(this));
                            return false;
                        } else if($thisSpan.hasClass("up")){
                            var myE = {};
                            var myData = {};
                            myE.data = myData;
                            myE.data.b = "up";
                            myE.data.funUse = true;
                            colSequence(myE, $(this));
                            return false;
                        }
                    }
                });
            };

            function pageRefresh(){
                var sM = $('div.grid_head_menu > div.grid_head_menu',$gBox).find('label');
                formatHead();
                $('table',$gView).remove();
                loading();
                if(defaults.pages.goPage || defaults.pages.paging){
                    var col = defaults.colums;
                    hids = [];
                    for(var i = 0; i < col.length; i++) {
                        formatHids(i);
                    };
                };
                initGrid(_data, 0, end-str, colAttributes);
                sM.each(function(){
                    if(!$(this).hasClass('chected')){
                        $(this).addClass('chected');
                    };
                });
            };

            function formatHead(){
                var cols = $('div.cols',$gHeader);
                var hidTh = $('div.cols:hidden',$gHeader);
                for(i = 0; i < cols.length; i++){
                    var wid = colAttributes.widths[i];
                    cols.eq(i).width(wid);
                };
                if(hidTh.length > 0){
                    hidTh.show();
                };
                if($checkBox.attr("checked")){
                    $checkBox.attr("checked", false);
                };
            };

            //这个方法是和isAllSelected配合使用的
            function isAllSelected(){
                for(var i = 0, len = allCh.length; i < len; i++){
                    if(allCh[i] == false){
                        return false;
                    };
                };
                return true;
            };

            //行点击触发的事件，好像是checkbox的事情，现在先不管？？？？？？
            function trClick(){
                $(this).unbind("click");
                if(defaults.multiselect){
                    var cInpt = $("td:first > input",$(this));
                    var theI = $(this).index() - 1;
                    if(defaults.radioSelect){
                        $(this).siblings().removeClass("select");
                    };
                    if($(this).hasClass('select')){
                        $(this).removeClass('select');
                        if(defaults.clickSelect){
                            cInpt.attr("checked",false);
                        }
                        allCh[theI] = false;
                    }else{
                        $(this).addClass('select');
                        if(defaults.clickSelect){
                            cInpt.attr("checked",true);
                        }
                        allCh[theI] = true;
                        trId = $(this).attr("id");
                    };
                    if(isAllSelected()){
                        $checkBox.attr("checked",true);
                    }else{
                        $checkBox.attr("checked",false);
                    };
                }else{
                    if($(this).hasClass('select')){
                        $(this).removeClass('select');
                    }else{
                        $(this).siblings().removeClass('select');
                        $(this).addClass('select');
                    };
                };
            };

            function allCheckedChange(isChecked){
                for(var i = 0, len = allCh.length; i < len; i++){
                    allCh[i] = isChecked;
                };
            };

            function allClick(){
                var trs = $("table tr",$gView);
                var cInpt = $("table td input[name=g_check]",$gView);
                if($(this).attr("checked")=="checked"){
                    cInpt.attr("checked",true);
                    trs.addClass('select');
                    allCheckedChange(true);
                }else{
                    cInpt.attr("checked",false);
                    trs.removeClass('select');
                    allCheckedChange(false);
                }
            };

            //加载动画
            function loading(){
                if($('div.loading',$gView).length > 0){
                    $load.show();
                }else{
                    if(defaults.height){
                        $load.css("padding-top",defaults.height/2 - 20);
                    }
                    $gView.append($load);
                };
            }

            function colSequence(e, $thisHdCo){
                var thisName = $(this).text();
                var isCanColPaiXu = true;
                for(var r = 0; r < colAttributes.sorts.length; r++) {
                    if(colAttributes.text[r] == thisName) {
                        if(colAttributes.sorts[r] == "disable") {
                            isCanColPaiXu = false;
                            break;
                        }
                    }
                }
                if(!isCanColPaiXu) {
                    //alert("该列不能排序！");
                    isCanColPaiXu = true;
                    return false;
                }
                if(e.data != null) {
                    if(e.data && e.data.funUse) {

                    } else {
                        var path = e.data.b;
                        var tem = judgeMenu(path);
                        if(tem == false) {
                            return true;
                        }
                    }

                }
                var isAsc;
                var sorts = [];
                var way = "";
                var ways = "";
                if( e.data && e.data.funUse) {
                    way = $thisHdCo.children("span");
                    ways = $thisHdCo.siblings().find("span");
                } else {
                    way = $(this).children("span");
                    ways = $(this).siblings().find("span");
                }
                //var way = $(this).children("span");
                //var ways = $(this).siblings().find("span");
                var listData = $('table > tbody > tr:not(:first)', $gView);
                var tdIndex = "";
                if(e.data && e.data.funUse) {
                    tdIndex = $thisHdCo.index();
                } else {
                    tdIndex = $(this).index();
                }
                listData.each(function(i){
                    var colsText = $(this).children('td').eq(tdIndex).text();
                    var obj = new sortInfo(i, colsText);
                    sorts.push(obj);
                });
                ways.removeClass();

                if(way.parent().text() != "升序排序" && way.parent().text() != "降序排序") {
                    if(way.hasClass('up')){
                        if(e.data && e.data.funUse) {
                        } else {
                            way.removeClass('up').addClass('down');
                        }
                    }else{
                        if(e.data && e.data.funUse) {
                        } else {
                            way.removeClass('down').addClass('up');
                        }

                    }
                }

                if(way.hasClass('down')){
                    isAsc = true;
                }else{
                    isAsc = false;
                };
                sortFn(sorts,isAsc);
                var sortedTrs = "";
                $('table > tbody > tr:not(:first)',$gView).remove();
                listData.removeClass("odd");
                for(var i=0, len=sorts.length; i<len; i++){
                    var j = i + 1 & 1;
                    var index = sorts[i].index;
                    if(defaults.number){
                        $('td.number', listData.get(index))
                            .attr("title",i + 1)
                            .children('span').text(i + pB*defaults.rowNum + 1).attr("title", i + pB*defaults.rowNum + 1);
                    };
                    if(j == 0){
                        listData.eq(index).addClass("odd");
                    }
                    sortedTrs = sortedTrs + listData.get(index).outerHTML;
                };
                listData.remove();
                $('table > tbody',$gView).append(sortedTrs);
            };

            var upFlag = false;
            var downFlag = false;

            function judgeMenu(tem){
                if(tem == "up") {
                    if(!upFlag) {
                        upFlag = true;
                        downFlag = false;
                        return true;
                    }
                    return false;
                } else {
                    if(!downFlag) {
                        downFlag = true;
                        upFlag = false;
                        return true;
                    }
                    return false;
                }

            }

            //快速排序
            function sortFn(arr,isAsc){
                return quickSort(arr, 0, arr.length - 1);
                function quickSort(arr,l,r){
                    if(l<r){
                        var mid=arr[parseInt((l+r)/2)].text,
                            i=l-1,
                            j=r+1;
                        while(true){
                            if(isAsc){
                                while(arr[++i].text < mid);
                                while(arr[--j].text > mid);
                            }else{
                                while(arr[++i].text > mid);
                                while(arr[--j].text < mid);
                            }
                            if(i>=j)break;
                            var temp=arr[i];
                            arr[i]=arr[j];
                            arr[j]=temp;
                        }
                        quickSort(arr,l,i-1);
                        quickSort(arr,j+1,r);
                    }
                    return arr;
                };
            };

            //siblings找的是同辈的元素，挺好用的，用到它好几次了。
            function changeNeedle(){
                $(this)
                    .addClass('hover')
                    .siblings().removeClass('th_change');
                var offset = $(this).offset();
                var mL = parseInt($gHeader.css("margin-left"));
                var x = offset.left - g.offset().left + $(this).width() + Math.abs(mL) - 5;
                var y = offset.top - g.offset().top - 1;
                $needle.css({'left':x,'top':y});
                $(this).addClass('th_change');
            };

            function unHover(){
                $(this).removeClass('hover');
            };

            //列排序
            function moveThead(e){
                var $T = $(this);
                $T.siblings(".cols").addClass("can");
                $T.removeClass("can next");
                $T.nextAll().addClass("next");
                var oldL = $(this).offset().left;
                var oldT = $(this).offset().top;
                var $Tt = $('<div class="g_indicator no">' + $T.text() + '</div>');
                var $P = $('<div class="g_pointer"></div>');
                $gBox.append($Tt,$P);
                _move = true;
                _x = $T.offset().left;
                $(document).mousemove(function(e){
                    var $e = $(e.target);
                    var has = $e.hasClass("can");
                    var par = $e.parent().hasClass("can");
                    var nex = $e.hasClass("next");
                    var pne = $e.parent().hasClass("next");
                    var l,t;
                    if(_move){
                        x = e.clientX;
                        y = e.clientY + 20;
                        $Tt.css({"left":x,"top":y});
                        if(nex || pne){
                            $Tt.removeClass("no");
                            if(nex){
                                l = $e.offset().left +  $e.width() - g.offset().left - 8;
                                t = $e.height();
                            }else{
                                l = $e.parent().offset().left +  $e.parent().width() - g.offset().left - 8;
                                t = $e.parent().height();
                            };
                            $P.css({"left":l,"top":t});
                        }else if(has || par){
                            $Tt.removeClass("no");
                            if(has){
                                l = $e.offset().left - g.offset().left - 8;
                                t = $e.height();
                            }else{
                                l = $e.parent().offset().left - g.offset().left - 8;
                                t = $e.parent().height();
                            }
                            $P.css({"left":l,"top":t});
                        }else{
                            $Tt.addClass("no");
                            $P.attr("style","");
                        };
                    };
                    return false;
                }).mouseup(function(e){
                        _move=false;
                        $(document).unbind('mousemove mouseup');
                        if($Tt.hasClass("no")){
                            $Tt.animate({top:oldT,left:oldL,opacity:0},400).queue(function(){
                                $Tt.remove();
                                $P.remove();
                            });
                        }else{
                            var point = $(e.target);
                            var oCol = $T.index();
                            var nex = point.hasClass("next") || point.parent().hasClass("next");
                            var bOra = true;
                            $Tt.remove();
                            $P.remove();
                            if(point.parent().is(".cols")){
                                point = point.parent();
                            };
                            var nCol = point.index();
                            if(nex){
                                $T.insertAfter(point);
                                bOra = false;
                            }else{
                                $T.insertBefore(point);
                            };
                            $T.siblings().removeClass("can next");
                            formatCol(oCol,nCol,bOra);
                            if(hids.length > 0){
                                if(defaults.multiselect){
                                    oCol = oCol - 1;
                                    nCol = nCol - 1;
                                };
                                if(defaults.number){
                                    oCol = oCol - 1;
                                    nCol = nCol - 1;
                                };
                                var temp = hids[oCol];
                                hids.splice(oCol,1);
                                hids.splice(nCol,0,temp);
                            };
                        }
                    });
                return false;
            };

            //打印obj
            function testObj(arr){
                var result = "";
                for(var i=0;i<arr.length;i++){
                    result += arr[i].getInfo() + ", ";
                }
                return result;
            };

            function sortInfo(index,text){
                this.index = index;
                this.text = text;
                this.getInfo = function(){
                    return this.index + ": " + this.text;
                };
            }
            //排td位置
            function formatCol(oCol,nCol,bOra){
                var $t = $('table > tbody > tr',$gView);
                $t.each(function(){
                    var oT = $(this).children("td");
                    if(bOra){
                        oT.eq(oCol).insertBefore(oT.eq(nCol));
                    }else{
                        oT.eq(oCol).insertAfter(oT.eq(nCol));
                    };
                });
            }

            //生成menu
            function greatMenu(){
                var gMb = '<div class="grid_head_menu"><ul>';
                if(defaults.sorts){
                    gMb += '<li><div><span class="up"></span>升序排序</div></li><li><div><span class="down"></span>降序排序</div></li>';
                };
                if(defaults.colDisplay){
                    gMb += '<li class="parting"><div><b class="parent"></b><span class="veiw"></span>列显示</div></li></ul>';
                    gMb += '<div class="grid_head_menu"><ul>';
                    for(i = 0;i < defaults.colums.length; i++){
                        var text = defaults.colums[i].text ;
                        gMb += '<li><div><label class="chected">' + text + '</label></div></li>';
                    };
                    gMb += '</div>';
                };
                gMb += '</div>';
                $gBox.prepend(gMb);
                $('div.grid_head_menu > ul > li > div:not(.grid_head_menu)',$gBox)
                    .bind('mouseover',function(){$(this).addClass('hover');})
                    .bind('mouseout',function(){$(this).removeClass('hover');});
                $('.grid_head_menu .up', $gBox).parent().bind("click", {b:'up'} ,colSequence);
                $('.grid_head_menu .down', $gBox).parent().bind("click", {b:'down'} ,colSequence);
            };

            function dragCol(e){
                $('div.grid_head_menu',$gBox).hide();
                var $c = $("div.th_change",$gHeader);
                var mL = parseInt($gHeader.css("margin-left"));
                var oldW = $c.width();
                var index = $c.index();
                var colL = $c.position().left;
                var colW = $c.width();
                var $rL = $('<div class="ruler left"></div>');
                var $rR = $('<div class="ruler right"></div>');
                var x,y,p;
                if($('div.ruler',$gView).length > 0){
                    $rL = $('div.left',$gBox);
                    $rR = $('div.right',$gBox);
                    $rL.show().css("left",colL + mL + $gView.scrollLeft() - 1);
                    $rR.show().css("left",colL + colW + $gView.scrollLeft() + mL);
                }else{
                    $rL.css("left",colL - 1);
                    $rR.css("left",colL + colW);
                    $gView.append($rL,$rR);
                };
                _move = true;
                _x = $c.offset().left;
                p = _x - colL;
                $(document).mousemove(function(e){
                    if(_move){
                        x = e.pageX - p + mL + $gView.scrollLeft();//移动时根据鼠标位置计算控件左上角的绝对位置
                        if(x >= colL + mL + 20 + $gView.scrollLeft()){
                            $rR.css("left", x);
                        };
                    };
                    return false;
                }).mouseup(function(e){
                        _move=false;
                        $(document).unbind('mousemove mouseup');
                        $rL.hide();
                        $rR.hide();
                        if(e.pageX - _x > 20){
                            $c.width(e.pageX - _x);
                        }else{
                            $c.width(20);
                        };
                        oW = oldW;
                        nW = $c.width();
                        tI = index;
                        formatWidth(oldW, nW, index);
                        if(defaults.multiselect)
                            tI = index - 1;
                        if(defaults.number)
                            tI = index - 1;
                        colAttributes.widths.splice(tI - 1, 1, nW);
                        var sL = $gView.scrollLeft();
                        $gHeader.css('margin-left',-sL);
                        if($("table", $gView).width() < $gBox.width()) {

                            var cWidth = $c.width();
                            var newWidth = cWidth + $gBox.width() - $("table", $gView).width();
                            $c.width(newWidth);
                            var $f = $('table > tbody > tr:first > td', $gView);
                            $f.eq(index).width(newWidth);

                            if($(".rowEditorDiv", $gView).length > 0) {
                                var itIndex = $c.index();
                                $(".rowEditorDiv", $gView).find(".inner_div").each(function(i){
                                    if(i == itIndex) {
                                        if($(this).find("input").attr("id")) {
                                            $(this).width(newWidth).find("input").width(newWidth - 26);
                                        } else {
                                            $(this).width(newWidth).find("input").width(newWidth - 6);
                                        }
                                        var oldRowWidth = $(".rowEditorDiv", $gView).width();
                                        $(".rowEditorDiv", $gView).width(oldRowWidth + newWidth - oW);
                                    }
                                });
                            }

                        } else {
                            if($(".rowEditorDiv", $gView).length > 0) {
                                var itIndex = $c.index();
                                $(".rowEditorDiv", $gView).find(".inner_div").each(function(i){
                                    if(i == itIndex) {
                                        if($(this).find("input").attr("id")) {
                                            $(this).width(nW).find("input").width(nW - 26);
                                        } else {
                                            $(this).width(nW).find("input").width(nW - 6);
                                        }
                                        var oldRowWidth = $(".rowEditorDiv", $gView).width();
                                        $(".rowEditorDiv", $gView).width(oldRowWidth + nW - oW);
                                    }
                                });
                            }
                        }
                        //判断此时是否有rowEditor，如果有的话，那么相应的列的宽度变宽，总宽度也跟着改变
                        //$c就是改变的列的jQuery对象，我们要对$c所对应的input进行修改，然后改变整个div的宽度


                    });
                return false;
            };

            //改变td宽度
            function formatWidth(oldW,newW,i){
                var $f = $('table > tbody > tr:first > td', $gView);
                var tW = $('table',$gView).width();
                var z = tW + (newW - oldW);
                var n = tW - (oldW - newW) ;
                $f.eq(i).width(newW);
                if(newW > oldW){
                    $('table',$gView).width(z);
                    $gHeader.width(z);
                } else{
                    $('table',$gView).width(n);
                    if(n > $gBox.width())
                        $gHeader.width(n);
                };
                if($checkBox.attr("checked")){
                    $checkBox.attr("checked",false);
                };

            };

            //计算滚动条(一般是不会出现滚动条的)
            function scrollWidth(){
                if(end == "auto"){
                    var url = defaults.url;
                    var dataType = defaults.dataType;
                    $.ajax({
                        url: url,
                        dataType: dataType,
                        async : defaults.isAsync,
                        //async:false,
                        success: function(data){
                            end = data.rows.length;
                            _data = data;
                            _stop = end;
                        }
                    });
                };
                if(defaults.height < end * 21){
                    return true;
                }else{
                    return false;
                };
            }

            //算header中td宽度
            function autoWidth(){
                var col = defaults.colums;
                var check = defaults.multiselect; //判断是否需要checkbox
                var num = defaults.number;  //是否需要序列号
                var allW = 0;
                if(defaults.width){
                    if(defaults.width == 'auto'){
                        allW = $gW;
                    }else{
                        allW = defaults.width;
                    };
                }else{
                    allW = $gW;
                };
                var j = 0,b = 0;//b的作用是记录共有表格共有多少列，包含序号列和checkbox，如果有的话
                var hasWidth = 0;
                for(i = 0; i < col.length; i++){
                    if(col[i].width){
                        hasWidth = parseInt(hasWidth) + parseInt(col[i].width);
                    }else{
                        j++; //如果没有给列定义宽度，那么j++
                    };
                    b++; //每循环一次b加1
                };
                if(check){
                    allW = allW - 22;
                    b++;
                };
                if(num){
                    allW = allW - 30;
                    b++;
                };
                if(defaults.height == 'auto'){
                    adaptive = Math.abs((allW - b - hasWidth) / j);
                }else{
                    if(scrollWidth()){
                        adaptive = Math.abs((allW - b - hasWidth - 17) / j);
                    }else{
                        adaptive = Math.abs((allW - b - hasWidth) / j);
                    };
                };

            }

            function reGreadPage() {
                var $gPage = $('<div class="grid_page"><div class="grid_page_box"></div></div>');
                var $pBox = $('div.grid_page_box',$gPage);
                var $p = $('<div class="prick"></div>');
                if(defaults.pages.goPage) {
                    var goTo = '<div class="grid_entry"><select name="grid_pages">';
                    for(i = 1; i < ps + 1; i++){
                        goTo = goTo + '<option>' + i + '</option>';
                    };
                    goTo = goTo + '</select></div>';
                    $pBox.append(goTo,'<div class="prick"></div>');
                    pSel = $('select', $pBox);
                    $($pBox).find("select").val(pB);
                    pSel.bind('change', {b:'select'}, jampPage);
                };
                if(defaults.pages.paging){
                    var $pagings = $('<span><b class="grid_page_fist"></b></span><span><b class="grid_page_prev"></b></span><div class="prick"></div><div class="grid_note"><input name="" type="text" class="page_nub" value="1" />页 共 '+ ps +' 页</div><div class="prick"></div><span><b class="grid_page_next"></b></span><span><b class="grid_page_last"></b></span><div class="prick"></div>');
                    pFirst = $('b.grid_page_fist',$pagings);
                    pPrev = $('b.grid_page_prev',$pagings);
                    pNub = $('input.page_nub',$pagings);
                    pNext = $('b.grid_page_next', $pagings);
                    pLast = $('b.grid_page_last', $pagings);
                    $pBox.append($pagings);
                    pNub.val(pB);
                    pFirst.bind("click",{b:'first'},pageFn);
                    pPrev.bind("click",{b:'prev'},pageFn);
                    pNext.bind("click",{b:'next'},pageFn);
                    pLast.bind("click",{b:'last'},pageFn);
                    pNub.bind('keyup',{b:'nub'},jampPage);
                };
                if(defaults.pages.renew){
                    var $refresh = $('<span title="刷新"><b class="grid_refresh"></b></span>');
                    $pBox.append($refresh,$p);
                    $refresh.bind('click',pageRefresh);
                };
                if(defaults.pages.info){
                    var $info = $('<div class="grid_info">每页显示 '+ defaults.rowNum +' 条数据 - 共 '+ total +' 条数据</div>');
                    $pBox.append($info);
                };
                $gBox.append($gPage);
            };

            return ({
                grid:{
                    getDataTotalCount : function () {
                        return _data.total;
                    },

                    //条件查询
                    queryData : function(queryCondition) {
                        //queryCondition的样子{url:"", type:"",condition:{key:value, key:value}}
                        //首先判断是前端查询还是后端查询
                        if(queryCondition.frontQuery == true || queryCondition.frontQuery == "true") {
                            //前端查询

                        } else {
                            //后端查询
                            var newObj = {};
                            newObj.pageNo = pB + 1;
                            newObj.rowNum = defaults.rowNum;
                            //    newObj.queryData = defaults.queryData;
                            newObj = $.extend(queryCondition.conditon, newObj);
                            $.ajax({
                                url: queryCondition.url ? queryCondition.url : defaults.url,
                                dataType: "json",
                                type : queryCondition.type ? queryCondition.type : "GET",
                                data : newObj,
                                async : defaults.isAsync,
                                error: function (XMLHttpRequest, errorThrown) {
                                    alert("数据加载出错！" + errorThrown);
                                },
                                success: function(myData){
                                    _data = myData;
//	                                if(_data.total < pB * defaults.rowNum) {
//	                                    end = _data.total;
//	                                } else {
//	                                    end = pB * defaults.rowNum;
//	                                }
                                    end = defaults.rowNum;
                                    cancelCheck();
                                    $('table', $gView).remove();
                                    //hideCol(true);
                                    initGrid(myData, 0, end, colAttributes);
                                    $('.grid_page').remove();
                                    if(defaults.pager) {
                                        greadPage();
                                    }
                                }
                            });
                        }
                    },

                    getChecked : function() {
                        var rows = [];
                        $gView.find("tr").each(function(){
                            var $thisCheckbox = $(this).find("input[type='checkbox']").eq(0);
                            if( $thisCheckbox.length > 0 && $thisCheckbox.attr("checked") == "checked" ) {
                                //说明此checkbox被选中了
                                var row = {};
                                if(col_name_is_id) {
                                    for(var u = 0; u < defaults.colums.length; u++) {
                                        row[defaults.colums[u].name] = $(this).find("td").eq(u + 2).text();
                                    }
                                } else {
                                    var trId = $(this).attr("id");
                                    row.id = trId.replace(/row_/, "");
                                    for(var u = 0; u < defaults.colums.length; u++) {
                                        row[defaults.colums[u].name] = $(this).find("td").eq(u + 2).text();
                                    }
                                }
                                rows.push(row);
                            }
                        });
                        return rows;
                    },

                    delGridData : function(delUrl, dataType, theThis) {
                        defaults.callback.beforeDelData();
                        var trId = $(theThis).parents("tr").attr("id");
                        var idArray = trId.split("row_");
                        $.ajax({
                            url: delUrl,
                            dataType: "text",
                            type: "POST",
                            async : defaults.isAsync,
                            data:"dataId=" + idArray[1],
                            beforeSend : function(){

                            },
                            error: function (XMLHttpRequest, errorThrown) {
                                alert("删除失败！");
                            },
                            success: function(myData){
                                if(myData == "success") {
                                    if(!defaults.callback.afterDelData()){
                                        $(theThis).parents('tr').remove();
                                        if($(".grid_view").find("tr").length > 1) {
                                            var obj = {};
                                            obj.pageNo = pB + 1;
                                            obj.rowNum = defaults.rowNum;
                                            //     obj.queryData = defaults.queryData;
                                            obj = $.extend(defaults.data,obj);
                                            $.ajax({
                                                type: defaults.type,
                                                url: defaults.url,
                                                async : defaults.isAsync,
                                                dataType: "json",
                                                data : obj,
                                                success: function(loadData) {
                                                    $('table',$gView).remove();
                                                    if(!defaults.afterRepage) {
                                                        //前端分页
                                                        var dataStart = (pB - 1)*defaults.rowNum + 1;
                                                        var dataEnd = 0;
                                                        if(loadData.rows.length < pB*defaults.rowNum) {
                                                            dataEnd = loadData.rows.length;
                                                            if(!defaults.afterRepage) {
                                                                originGridData = loadData;
                                                            }
                                                            initGrid(loadData, dataStart, dataEnd, colAttributes);
                                                        } else {
                                                            dataEnd = pB * defaults.rowNum;
                                                            if(!defaults.afterRepage) {
                                                                originGridData = loadData;
                                                            }
                                                            initGrid(loadData, dataStart, dataEnd, colAttributes);
                                                        }
                                                    } else {
                                                        var dataEnd = (pB-1)*defaults.rowNum + loadData.rows.length;
                                                        if(!defaults.afterRepage) {
                                                            originGridData = loadData;
                                                        }
                                                        initGrid(loadData, 0, dataEnd, colAttributes);
                                                    }
                                                    $(".grid_page").remove();
                                                    reGreadPage();
                                                },
                                                error:function(XMLHttpRequest, errorThrown) {
                                                    alert("数据加载出错！" + errorThrown);
                                                }
                                            });
                                        } else {
                                            //传入的页码是上一页
                                            var obj = {};
                                            obj.pageNo = pB + 1;
                                            obj.rowNum = defaults.rowNum;
                                            //      obj.queryData = defaults.queryData;
                                            obj = $.extend(defaults.data,obj);
                                            $.ajax({
                                                type: defaults.type,
                                                url: defaults.url,
                                                dataType: "json",
                                                async : defaults.isAsync,
                                                data: obj,
                                                success: function(loadData) {
                                                    var on = "unclick";
                                                    if(pB != 1) {
                                                        pB = pB - 1;
                                                    }
                                                    str = (pB - 1) * defaults.rowNum;
                                                    ps = Math.ceil(loadData.total/defaults.rowNum);
                                                    total = loadData.total;
                                                    $(".grid_page").remove();
                                                    reGreadPage();
                                                    if((ps == pB && ps == 1) || ps == 0) {
                                                        pNext.removeClass(on);
                                                        pLast.removeClass(on);
                                                        pFirst.removeClass(on);
                                                        pPrev.removeClass(on);
                                                        pNext.addClass(on);
                                                        pLast.addClass(on);
                                                        pFirst.addClass(on);
                                                        pPrev.addClass(on);
                                                    } else if(ps == pB) {
                                                        pNext.removeClass(on);
                                                        pLast.removeClass(on);
                                                        pFirst.removeClass(on);
                                                        pPrev.removeClass(on);
                                                        pNext.addClass(on);
                                                        pLast.addClass(on);
                                                    } else if(pB == 1) {
                                                        pNext.removeClass(on);
                                                        pLast.removeClass(on);
                                                        pFirst.removeClass(on);
                                                        pPrev.removeClass(on);
                                                        pFirst.addClass(on);
                                                        pPrev.addClass(on);
                                                    }
                                                    $('table',$gView).remove();
                                                    if(defaults.frontJumpPage) {
                                                        var dataStart = (pB - 1)*defaults.rowNum + 1;
                                                        var dataEnd = pB * defaults.rowNum;
                                                        if(!defaults.afterRepage) {
                                                            originGridData = loadData;
                                                        }
                                                        initGrid(loadData, dataStart, dataEnd, colAttributes);
                                                    } else {
                                                        if(!defaults.afterRepage) {
                                                            originGridData = loadData;
                                                        }
                                                        var dataEnd = (pB-2)*defaults.rowNum + loadData.rows.length;
                                                        initGrid(loadData, 0, dataEnd, colAttributes);
                                                    }

                                                },
                                                error:function(XMLHttpRequest, errorThrown) {
                                                    alert("数据加载出错！" + errorThrown);
                                                }
                                            });

                                        }

                                    }
                                }
                            }
                        });
                    },

                    clickId : function(){
                        return trId;
                    },

                    selectTrs : function(obj){
                        var objs = obj.split(",");
                        for(var i=0;i < objs.length;i++){
                            var id = objs[i];
                            var trId = $('tr#'+id,g);
                            if(trId.hasClass("select")){
                                return;
                            }else{
                                trId.trigger('click');
                            };
                        }
                    },

                    reloadGrid : function(girdData) {
                        if(girdData){
                            _data = defaults.girdData;
                            if(_data.total < pB * defaults.rowNum) {
                                end = _data.total;
                            } else {
                                end = pB * defaults.rowNum;
                            }
                            cancelCheck();
                            $('table', $gView).remove();
                            initGrid(_data, 0, 10, colAttributes);
                            $('.grid_page').remove();
                            if(defaults.pager) {
                                reGreadPage();
                            }
                        } else {
                            var newObj = {};
                            newObj.pageNo = pB + 1;
                            newObj.rowNum = defaults.rowNum;
                            //    newObj.queryData = defaults.queryData;
                            newObj = $.extend(defaults.queryData,newObj);
                            $.ajax({
                                url: defaults.url,
                                dataType: "json",
                                type : defaults.type,
                                async : defaults.isAsync,
                                data : newObj,
                                error: function (XMLHttpRequest, errorThrown) {
                                    alert("数据加载出错！" + errorThrown);
                                },
                                success: function(myData){
                                    _data = myData;
                                    if(_data.total < pB * defaults.rowNum) {
                                        end = _data.total;
                                    } else {
                                        end = pB * defaults.rowNum;
                                    }
                                    //alert(end);
                                    cancelCheck();
                                    $('table', $gView).remove();
                                    initGrid(_data, 0, end, colAttributes);
                                    $('.grid_page').remove();
                                    if(defaults.pager) {
                                        reGreadPage();
                                    }
                                }
                            });
                        }


                    },

                    checkAll : function(gridId) {
                        $("#"+gridId).find(".chex").attr("checked", "checked");
                        $("#"+gridId).find("table").find("tr").each(function(){
                            if(!$(this).hasClass("select")) {
                                $(this).trigger("click");
                            }
                        });
                    },

                    cancelAllChecked : function(gridId){
                        $("#"+gridId).find(".chex").attr("checked", false);
                        $("#"+gridId).find("table").find("tr").each(function(){
                            if($(this).hasClass("select")) {
                                $(this).trigger("click");
                            }
                        });
                    },

                    delMulGridData : function(gridId, delUrl,successMsg,errorMsg){
                        //首先得到所有checkbox的id，拼装成一个id的字符串
                        //然后往后台传值，他给我返回新的数据
                        var allId = "";
                        var temGrid = $("#"+gridId);
                        //下面这个变量用来记录一共删除多少条
                        var itemNum = 0;
                        $(temGrid).find("table").eq(0).find("input[type='checkbox']").each(function(){
                            if($(this).attr("checked")) {
                                itemNum++;
                                allId = allId + $(this).parents("tr").attr("id") + ",";
                            }
                        });
                        if(allId != ""){
                            allId = allId.substring(0, allId.length-1);
                        }

                        $.ajax({
                            url: delUrl,
                            dataType: "text",
                            type: "GET",
                            data:"allId=" + allId,
                            async : defaults.isAsync,
                            beforeSend : function(){
                            },
                            error: function (XMLHttpRequest, errorThrown) {
                                if(errorMeagess){
                                    msgAlert("Alert", errorMsg);
                                }else{
                                    msgAlert("Alert", "删除失败！");
                                };
                            },
                            success: function(myData){
//我得想办法能让他传一个删除成功的提示
                                if(myData == "success") {
                                    if(successMsg){
                                        msgSuccess("Success", successMsg);
                                        return;
                                    }else{
                                        msgSuccess("Success", "删除成功！");
                                    }
                                    if(!defaults.callback.afterDelData()){
                                        if($(".grid_view").find("tr").length > itemNum + 1) {
                                            var obj = {};
                                            obj.pageNo = pB + 1;
                                            obj.rowNum = defaults.rowNum;
                                            //    obj.queryData = defaults.queryData;
                                            obj = $.extend(defaults.data,obj);
                                            $.ajax({
                                                type: defaults.type,
                                                url: defaults.url,
                                                dataType: "json",
                                                async : defaults.isAsync,
                                                data: obj,
                                                success: function(loadData) {
                                                    $('table',$gView).remove();
                                                    var dataEnd = (pB-1)*defaults.rowNum + loadData.rows.length;
                                                    if(defaults.frontJumpPage) {
                                                        //前端分页
                                                        var dataStart = (pB - 1)*defaults.rowNum + 1;
                                                        var dataEnd = 0;
                                                        if(loadData.rows.length < pB*defaults.rowNum) {
                                                            dataEnd = loadData.rows.length;
                                                            initGrid(loadData, dataStart, dataEnd, colAttributes);
                                                        } else {
                                                            dataEnd = pB * defaults.rowNum;
                                                            initGrid(loadData, dataStart, dataEnd, colAttributes);
                                                        }
                                                    } else {
                                                        var dataEnd = (pB-1)*defaults.rowNum + loadData.rows.length;
                                                        initGrid(loadData, 0, dataEnd, colAttributes);
                                                    }
                                                    $(".grid_page").remove();
                                                    reGreadPage();
                                                },
                                                error:function(XMLHttpRequest, errorThrown) {
                                                    alert("数据加载出错！" + errorThrown);
                                                }
                                            });
                                        } else {
                                            //传入的页码是上一页
                                            var obj = {};
                                            if(pB - 1 == 0) {
                                                obj.pageNo = pB + 1;
                                            } else {
                                                obj.pageNo = pB;
                                            }

                                            obj.rowNum = defaults.rowNum;
                                            //       obj.queryData = defaults.queryData;
                                            obj = $.extend(defaults.data,obj);
                                            $.ajax({
                                                type: defaults.type,
                                                url: defaults.url,
                                                dataType: "json",
                                                async : defaults.isAsync,
                                                data:obj,
                                                success: function(loadData) {
                                                    var on = "unclick";
                                                    if(pB != 1) {
                                                        pB = pB - 1;
                                                    }
                                                    str = (pB - 1) * defaults.rowNum;
                                                    ps = Math.ceil(loadData.total/defaults.rowNum);
                                                    total = loadData.total;
                                                    $(".grid_page").remove();
                                                    reGreadPage();
                                                    if((ps == pB && ps == 1) || ps == 0) {
                                                        pNext.removeClass(on);
                                                        pLast.removeClass(on);
                                                        pFirst.removeClass(on);
                                                        pPrev.removeClass(on);
                                                        pNext.addClass(on);
                                                        pLast.addClass(on);
                                                        pFirst.addClass(on);
                                                        pPrev.addClass(on);
                                                    } else if(ps == pB) {
                                                        pNext.removeClass(on);
                                                        pLast.removeClass(on);
                                                        pFirst.removeClass(on);
                                                        pPrev.removeClass(on);
                                                        pNext.addClass(on);
                                                        pLast.addClass(on);
                                                    } else if(pB == 1) {
                                                        pNext.removeClass(on);
                                                        pLast.removeClass(on);
                                                        pFirst.removeClass(on);
                                                        pPrev.removeClass(on);
                                                        pFirst.addClass(on);
                                                        pPrev.addClass(on);
                                                    }
                                                    $('table',$gView).remove();
                                                    if(defaults.frontJumpPage) {
                                                        var dataStart = (pB-1)*defaults.rowNum + 1;
                                                        var dataEnd = pB*defaults.rowNum;
                                                        if(!defaults.afterRepage) {
                                                            originGridData = loadData;
                                                        }
                                                        initGrid(loadData, dataStart, dataEnd, colAttributes);
                                                    } else {
                                                        var dataEnd = (pB-2)*defaults.rowNum + loadData.rows.length;
                                                        if(!defaults.afterRepage) {
                                                            originGridData = loadData;
                                                        }
                                                        initGrid(loadData, 0, dataEnd, colAttributes);
                                                    }

                                                },
                                                error:function(XMLHttpRequest, errorThrown) {
                                                    alert("数据加载出错！" + errorThrown);
                                                }
                                            });

                                        }

                                    }
                                }
                            }
                        });

                    }
                }
            });
        }
    });
})(jQuery);