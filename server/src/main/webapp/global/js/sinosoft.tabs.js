/*
 * tabs控件
 *
 * Copyright (c) levone
 *
 * Date: 2012-03-20
 *
 */
(function ($) {
    $.fn.extend({
        tabs:function (opts) {
            var defaults = {
                closeTab:false, //是否含有关闭
                quckList:false, //是否有快速选择
                slideTab:false, //切换动画
                minHeight:'auto', //最小高度
                maxHeight:'auto'  //最大高度
            }
            var defaults = $.extend(defaults, opts);
            var tab = $(this);
            var oldList = tab.children('ul').html();
            var oldWidth = 0;
            tab.addClass('tabs_box');
            $('ul.tabs_titles li', tab).each(function () {
                if ($(this).attr('disabled') == 'disabled') {
                    $(this).addClass('disabled');
                }
            })
            tab.children('div').addClass('tabs_cont');
            tab.children('ul li:first').addClass('tabs_select');
            $('div:first', tab).addClass('tabs_show');
            tab.children('ul').addClass('tabs_titles').wrap('<div class="tab_scroll"></div>');
            $('ul.tabs_titles', tab).wrap('<div class="title_box"></div>');

            if (defaults.maxHeight != 'auto') {
                maxHeightFn();
            }
            ;
            if (defaults.minHeight != 'auto') {
                minHeightFn();
            }
            ;

            $('ul.tabs_titles li', tab).hover(function () {
                $(this).addClass('li_hover');
            }, function () {
                $(this).removeClass('li_hover');
            });
            $('ul.tabs_titles li:not(".disabled")', tab).live('click', function () {
                tabCut($(this));
            });

            if (defaults.closeTab == true) {
                $('ul.tabs_titles li:not(".disabled")', tab).wrapInner('<b class="has_close"></b>').prepend('<span class="close"></span>');
                $('ul.tabs_titles li.disabled', tab).wrapInner('<b></b>');
                var tabClose = $('ul.tabs_titles li span', tab);
                var txtTab = $('ul.tabs_titles li b', tab);
                tabClose.live('click', function (e) {
                    closeTba($(this));
                    e.stopPropagation();
                });
                tabClose.live({mouseenter:function () {
                    $(this).addClass('tabhover')
                }, mouseleave:function () {
                    $(this).removeClass('tabhover')
                }})
            } else {
                $('ul.tabs_titles li', tab).wrapInner('<b href="javascript:void(0)"></b>');
            }
            ;

            if (defaults.quckList == true) {
                quckListFn();
                $('div.quck_sec', tab).hover(function () {
                    $(this).addClass('hover');
                }, function () {
                    $(this).removeClass('hover');
                });
                $('div.quck_sec ul li', tab).hover(function () {
                    $(this).addClass('hover');
                }, function () {
                    $(this).removeClass('hover');
                });
                $('div.quck_sec li', tab).live('click', quckSlcFn)
            }

            //关闭标签方法
            function closeTba(theTba) {
                var showIndex = theTba.parent().index();
                if (theTba.parent().hasClass('tabs_select')) {
                    var theShow = $('div.tabs_show', tab);
                    if (theTba.parent().is($('ul.tabs_titles li:last-child', tab))) {
                        theShow.prev().addClass('tabs_show');
                        theTba.parent().prev().addClass('tabs_select');
                    } else {
                        theShow.next().addClass('tabs_show');
                        theTba.parent().next().addClass('tabs_select');
                    }
                    ;
                }
                ;
                //判断是否含有滚动的关闭状态
                if ($('div.has_scroll', tab).size() == 1) {
                    var selTabWidth = theTba.parent().width() + 13;
                    var ulTitle = $('ul.tabs_titles', tab);
                    var ulMarLeft = parseInt(ulTitle.css('margin-left'));
                    var speed = 200;
                    if (ulMarLeft >= -selTabWidth) {
                        ulTitle.animate({marginLeft:0}, speed);
                        if (ulTitle.width() - selTabWidth < $('div.tab_scroll', tab).width()) {
                            removeScroll();
                        } else {
                            ulTitle.width(ulTitle.width() - selTabWidth);
                        }
                        ;
                    } else {
                        ulTitle.animate({marginLeft:ulMarLeft + selTabWidth}, speed);
                        ulTitle.width(ulTitle.width() - selTabWidth);
                        oldWidth = oldWidth - selTabWidth;
                    }
                    ;
                }
                ;
                theTba.parent().remove();
                if ($.browser.msie) {
                    $('li.tabs_select', tab).trigger("click");
                }
                ;
                $('div.tabs_cont', tab).eq(showIndex).remove();
                if ($('ul.tabs_titles li', tab).size() == 1) {
                    $('ul.tabs_titles li', tab).addClass('tabs_select');
                    $('ul.tabs_titles li span', tab).remove();
                    $('ul.tabs_titles li b', tab).removeClass('has_close');
                    $('div.tabs_cont', tab).addClass('tabs_show');
                }
                ;
                if (defaults.quckList == true) {
                    $('div.quck_sec li', tab).eq(showIndex).remove();
                }
                ;
            }

            ;
            //切换标签方法
            function tabCut(theTab) {
                if ($('ul.tabs_titles li', tab).size() != 1) {
                    $('ul li.tabs_select', tab).removeClass('tabs_select');
                    theTab.addClass('tabs_select');
                    var tabIndex = theTab.index();
                    $('div.tabs_cont', tab).removeClass('tabs_show');
                    $('div.tabs_cont', tab).eq(tabIndex).addClass('tabs_show');
                }
                ;
            }

            ;
            //最大高度
            function maxHeightFn() {
                $('div.tabs_cont', tab).addClass('tabs_show').wrapInner('<div class="inner_box"></div>');
                $('div.inner_box', tab).each(function () {
                    if ($(this).height() > defaults.maxHeight) {
                        $(this).css({'overflow':'hidden', 'overflow-y':'auto', 'height':defaults.maxHeight})
                    }
                })
                $('div.tabs_cont', tab).removeClass('tabs_show');
                $('div.tabs_cont:first', tab).addClass('tabs_show');
            }

            ;
            //最小高度
            function minHeightFn() {
                if ($('div.inner_box', tab).size() == 0) {
                    $('div.tabs_cont', tab).addClass('tabs_show').wrapInner('<div class="inner_box"></div>');
                }
                ;
                $('div.inner_box', tab).each(function () {
                    if ($(this).height() < defaults.minHeight) {
                        $(this).css({'height':defaults.minHeight})
                    }
                    ;
                })
                $('div.tabs_cont', tab).removeClass('tabs_show');
                $('div.tabs_cont:first', tab).addClass('tabs_show');
            }

            ;
            //插入快捷选择
            function quckListFn() {
                $('ul.tabs_titles', tab).before('<div class="quck_sec"><ul>' + oldList + '</ul></div>');
            }

            ;
            //快捷选择方法
            function quckSlcFn() {
                var quckIndex = $(this).index();
                $('ul.tabs_titles li', tab).get(quckIndex).click();
            }

            ;
            //出现滑动tab
            function slideTab() {
                var tabScroll = $('div.tab_scroll', tab);
                var tabsTitles = $('ul.tabs_titles', tab);
                var tabPrev = $('<div class="tab_prev"></div>');
                var tabNext = $('<div class="tab_next"></div>');
                if ($('div.tab_prev', tabScroll).size() == 0) {
                    tabScroll.addClass('has_scroll').append(tabPrev).append(tabNext);
                }
                var maxMargin = oldWidth - $('div.title_box', tab).width();
                tabsTitles.width(oldWidth).css('margin-left', -maxMargin);
                tabPrev.hover(function () {
                    $(this).addClass('p_hover');
                }, function () {
                    $(this).removeClass('p_hover');
                })
                    .bind('mousedown', slideLeft);
                tabNext.hover(function () {
                    $(this).addClass('n_hover');
                },function () {
                    $(this).removeClass('n_hover');
                }).bind('click', slideRight);
            }

            ;
            //左滑动
            function slideLeft() {
                var ulLeft = parseInt($('ul.tabs_titles', tab).css('margin-left'));
                var slideSpeed = 80;
                var slideWith = ulLeft + slideSpeed;
                if (ulLeft < -slideSpeed) {
                    $('ul.tabs_titles', tab).animate({marginLeft:slideWith}, 100);
                } else {
                    $('ul.tabs_titles', tab).css('margin-left', 0)
                }
                ;
            }

            //右滑动
            function slideRight() {
                var ulLeft = parseInt($('ul.tabs_titles', tab).css('margin-left'));
                var slideSpeed = 80;
                var slideWith = ulLeft - slideSpeed;
                var maxMargins = $('ul.tabs_titles', tab).width() + ulLeft;
                var titleBox = $('div.title_box', tab).width();
                if (maxMargins > (titleBox + slideSpeed)) {
                    $('ul.tabs_titles', tab).animate({marginLeft:slideWith}, 100);
                } else {
                    $('ul.tabs_titles', tab).css('margin-left', -($('ul.tabs_titles', tab).width() - titleBox));
                }
                ;
            }

            ;
            //去除滑动
            function removeScroll() {
                var tabScroll = $('div.tab_scroll', tab);
                tabScroll.removeClass('has_scroll');
                $('div.tab_prev', tabScroll).remove();
                $('div.tab_next', tabScroll).remove();
                $('ul.tabs_titles', tabScroll).removeAttr('style');
                oldWidth = 0;
            }

            //导出添加tab方法
            jQuery.extend({
                addTabFn:function (tab, hasClose, nodeText, nodeId, nodeBody, nodeUrl) {
                    var addTabUl = $('ul.tabs_titles', tab);
                    var title_box = $('div.title_box', tab);
                    if (oldWidth == 0) {
                        $('ul.tabs_titles li', tab).each(function () {
                            oldWidth = oldWidth + $(this).width() + 12 + 1;
                        });
                    }
                    ;
                    if (hasClose == true) {
                        addTabUl.append('<li id="' + nodeId + '"><span class="close"></span><b class="has_close">' + nodeText + '</b></li>');
                    } else {
                        addTabUl.append('<li id="' + nodeId + '"><b>' + nodeText + '</b></li>');
                    }
                    ;
                    tab.append('<div class="tabs_cont">' + nodeBody + '</div>');
                    oldWidth = oldWidth + $('ul.tabs_titles li:last', tab).width() + 12 + 1;
                    $('ul.tabs_titles li:last', tab).trigger("click");
                    if (oldWidth > title_box.width()) {
                        slideTab();
                    }
                    ;
                }
            });

        }
    })
})(jQuery);
