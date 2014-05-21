/**
 * Created by IntelliJ IDEA.
 * User: Phoenix
 * Date: 12-7-5
 * Time: 上午11:02
 * Modeled message panel, include Alert, Confirm
 */

function msgAlert(title, html, callback) {
    msgInstance(0, false, title, html, callback);
}

function msgConfirm(title, html, callback) {
    msgInstance(1, true, title, html, callback);
}

function msgSuccess(title, html, callback) {
    msgInstance(2, false, title, html, callback);
}

function msgFailed(title, html, callback) {
    msgInstance(3, false, title, html, callback);
}

function msgInstance(type, confirm, title, html, callback) {
    $.fn.messager({ 'type':type, 'confirm':confirm, 'header':true, 'title':title, 'html':html, 'btnsRight':{ '确定':callback } }, "extends");
}

(function ($) {
    var self, messager, msgOptions, msgOverlay, msgHeader;
    $.fn.extend({
        'version':"1.0",
        messager:function (options, value) {
            if (options === "close")
                return this._msgClose();

            self = this;
            if (self.length <= 0 && value !== "extends") return false;
            self._msgInit = function () {

                self._msgCreate(options, value);
                self._msgOpen();
                return false;
            };
            if (self.prop("tagName") == "FORM") {

            } else if (self.prop("tagName") == "INPUT") {

                self.bind('click', self._msgInit);

            } else if (self.prop("tagName") == undefined) {

                self._msgInit();
                self._msgOpen();
                return false;

            } else {

                self.bind('click', self._msgInit);

            }
            self.close = function () {
                self._msgClose();
            };
            self.blur();
        },
        _msgCreate:function (options, value) {
            var scrollWidth = $("html").width() - $("body").width();
            msgOptions = $.extend({
                'type':0,
                'icon':"../images/icon.gif",
                'linkToClose':true,
                'draggable':true,
                'header':true,
                'headerText':"系统消息",
                'headerCloser':true,
                'opacity':0.2,
                'confirm':true,
                'modal':true,
                'width':320,
                'height':"auto",
                'zIndex':1000,
                'left':"center",
                'top':"center",
                'title':"Alert!",
                'html':"操作成功！",
                'btnsLeft':null,
                'btnsRight':null
            }, options);

            if (typeof value == 'string') value = "'" + value + "'";
            if (typeof options != 'object') eval("msgOptions." + options + " = " + value);


            self._msgClose();
            self._msgOverlay();
            messager = $('<div></div>').prependTo($(document.body)).addClass("sinosoft-ui-messager")
                .css({
                    'zIndex':msgOptions.zIndex
                }).bind("selectstart",function (event) {
                    event.preventDefault();
                }).after($('<div></div>').addClass("clear"));

            self._msgHeader();
            self._msgContent();
            self._msgButtons();
            self._msgPosition();
            self._msgResize();
            self._msgDraggable();
            /*if($.browser.mozilla)
             //$( document.body ).css({ width: $( window ).width(), height: $( window ).height(), overflow: "hidden" });
             else {
             $( 'html' ).css({ 'overflow': "hidden" });
             $( "body" ).css({ 'width': $( window ).width() - scrollWidth });
             }*/
        },
        _msgOpen:function () {
            messager.show();
            return false;
        },
        _msgClose:function () {
            $(messager).next(".clear").remove();
            $(messager).remove();
            $(msgOverlay).next(".clear").remove();
            $(msgOverlay).remove();
            /*if($.browser.mozilla)
             $( document.body ).css({ width: "", height: "", 'overflow': "" });
             else
             $( 'html' ).css({ 'overflow': "" });*/
            return false;
        },
        _msgResize:function () {
            $(window).resize(function () {
                self._msgPosition();
                msgOverlay.css({ 'width':$(document).width(), 'height':$(document).height() });
            });
        },
        _msgPosition:function () {
            messager.css({
                'width':msgOptions.width,
                'height':Math.max(messager.height(), msgOptions.height),
                'left':msgOptions.left == "center" ? ($(window).width() - (msgOptions.width == "auto" ? $(messager).width() : msgOptions.width)) / 2 : msgOptions.left,
                'top':msgOptions.top == "center" ? ($(window).height() - (msgOptions.height == "auto" ? $(messager).height() : msgOptions.height)) / 2 + $(document).scrollTop() : msgOptions.top
            });
        },
        _msgHeader:function () {

            msgHeader = $('<h4></h4>').appendTo(messager).addClass("msg-header font_yahei").css({
                'visibility':msgOptions.header ? "visible" : "hidden",
                'cursor':msgOptions.draggable ? "move" : "default"
            }).append($('<span></span>').addClass("text").html(msgOptions.headerText));
        },
        _msgDraggable:function () {
            if (msgOptions.draggable) {
                var header = $(messager).addClass("sinosoft-ui-draggable").find(".msg-header");
                (msgOptions.header ? header : messager.css({ 'cursor':"move" })).bind('mousedown', self._drag);
            }
        },
        _drag:function (e) {
            e = e || window.event;
            e.preventDefault();
            var x = e.pageX,
                y = e.pageY,
                maxX = $(document).width() - 5,
                maxY = $(document).height() - 5;

            var obj = $(".sinosoft-ui-draggable");
            $(document).bind('mousemove', dragging);
            $(document).bind('mouseup', function () {
                $(document).unbind('mousemove', dragging);
            });
            function dragging(e) {
                var left = parseFloat($(obj).position().left + (e.pageX < 15 ? 0 : e.pageX - x), 10),
                    top = parseFloat($(obj).position().top + (e.pageY < 15 ? 0 : e.pageY - y), 10);

                $(obj).css({
                    'left':e.pageX < 5 || left < 5 ? 0 : left + obj.width() > maxX ? maxX - obj.width() : left,
                    'top':e.pageY < 5 || top < 5 ? 0 : top + obj.height() > maxY ? maxY - obj.height() : top
                });
                //todo: 优化移动，实现拖拽页面滚动效果
                x = e.pageX, y = e.pageY;
            }
        },
        _msgContent:function () {
            var containerWidth = msgOptions.width - 60;
            var msgIcon = $('<div></div>').addClass("msg-icon");
            switch (msgOptions.type) {
                case 0:
                    $(msgIcon).html("").css({ 'color':"red" });
                    break;
                case 1:
                    $(msgIcon).addClass("msg-icon1");
                    break;
                case 2:
                    $(msgIcon).addClass("msg-success");
                    break;
                case 3:
                    $(msgIcon).addClass("msg-icon3");
                    break;
            }
            var msgContent = $('<div></div>').appendTo(messager).addClass("container")
                .css({ 'width':containerWidth })
                .append(msgIcon)//todo: 替换图标
                .append($('<div></div>').addClass("msg-content").css({ 'width':containerWidth - $(".msg-icon").width() })
                .append($('<h4></h4>').addClass("msg-title").html(msgOptions.title))
                .append($('<div></div>').addClass("msg-text").html(msgOptions.html)));

            if (msgOptions.linkToClose) $(msgContent).find("a, input").bind('click', function () {
                self._msgClose();
            });
        },
        _msgButtons:function () {

            var btnWrapper = $('<div></div>').appendTo(messager).addClass("button_wrapper"),
                btnPaneRight = $('<div></div>').addClass("button_pane floatright").appendTo(btnWrapper);
            if (typeof msgOptions.btnsLeft === 'object' && msgOptions.btnsLeft !== null) {
                var btnPaneLeft = $('<div></div>').addClass("button_pane floatleft").appendTo(btnWrapper);
                $.each(msgOptions.btnsLeft, function (key, value) {
                    btnPaneLeft.append($('<a href="."></a>').addClass("").html(key).bind('click', self._msgClose).bind('click', value));
                });
            }

            if (typeof msgOptions.btnsRight === 'object' && msgOptions.btnsRight !== null) {

                $.each(msgOptions.btnsRight, function (key, value) {
                    btnPaneRight.append($('<a href="."></a>').addClass("").html(key).bind('click', self._msgClose).bind('click', value));
                });
            } else {
                btnPaneRight.append($('<a href="."></a>').addClass("").html("确定").bind('click', self._msgClose).bind('click', self._msgClose));
            }
            if (msgOptions.confirm)
                $('<a href="."></a>').addClass("").html("取消").bind('click', self._msgClose).bind('click', self._msgClose).appendTo(btnPaneRight);
        },
        _msgOverlay:function () {
            msgOverlay = $($.browser.msie && $.browser.version < 7 ? '<iframe></iframe>' : '<div></div>').prependTo($(document.body)).addClass("sinosoft-ui-overlay")
                .css({
                    'zIndex':msgOptions.zIndex - 1,
                    'opacity':msgOptions.modal ? msgOptions.opacity : 0,
                    'width':$.browser.msie && $.browser.version < 8 ? $(document.body).width() : $(document).width(),
                    'height':$.browser.msie && $.browser.version < 8 ? $(document.body).height() : $(document).height()
                }).after($('<div></div>').addClass("clear"));
        }
    });
})(jQuery);