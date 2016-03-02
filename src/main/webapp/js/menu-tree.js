(function() {
    var __bind = 0;
    __bind = function(fn, me) {
        return function() {
            return fn.apply(me, arguments);
        };
    }, __slice = [].slice;

    (function($, window) {
        var Menutree = 0;
        Menutree = (function() {
            Menutree.prototype.defaults = {
                animate : true,
                speed : 500
            };

            function Menutree(el, options) {
                this._clickHandler = __bind(this._clickHandler, this);
                this.options = $.extend({}, this.defaults, options);
                this.$el = $(el);
                this._checkOptions();
                this.$el.addClass('menutree');
                this.firstLvlSubmenu = ".menutree > li";
                this.secondLvlSubmenu = ".menutree > li > ul > li";
                this.firstLvlSubmenuLink = "" + this.firstLvlSubmenu + " > div > a";
                this.secondLvlSubmenuLink = "" + this.secondLvlSubmenu + " > div > a";
                this._hideSubmenus();
                this._bindEvents();
            }

            Menutree.prototype._bindEvents = function() {
                return $(document).on('click.menutree', "" + this.firstLvlSubmenuLink + ", " + this.secondLvlSubmenuLink, this._clickHandler);
            };

            Menutree.prototype._unbindEvents = function() {
                return $(document).off('click.menutree');
            };

            Menutree.prototype._isFirstLevel = function(clickedEl) {
                if ($(clickedEl).parent().parent().hasClass('menutree')) {
                    return true;
                }
            };

            Menutree.prototype._clickHandler = function(event) {
                var clickedEl, submenuLevel;
                clickedEl = event.currentTarget.parentElement;
                submenuLevel = this._isFirstLevel(clickedEl) ? this.firstLvlSubmenu : this.secondLvlSubmenu;
                if (this._hasChildenAndIsHidden(clickedEl)) {
                    event.preventDefault();
                    return this._openSubmenu(submenuLevel, clickedEl);
                } else if (this._isCurrentlyOpen(clickedEl)) {
                    event.preventDefault();
                    return this._closeSubmenu(clickedEl);
                } else {
                    event.preventDefault();
                    return this._selectTopmenu(submenuLevel, clickedEl);
                }
            };

            Menutree.prototype._openSubmenu = function(el, clickedEl) {
                var $clickedNestedMenu, $firstNestedMenu, $lastNestedMenu;
                $firstNestedMenu = $(el).find(' > ul');
                $lastNestedMenu = $(el).find(' > ul > li > ul');
                $clickedNestedMenu = $(clickedEl).next('ul');
                $(el).removeClass('roof-select');
                $(el).removeClass('root-pandect');
                $(clickedEl).parent().addClass('roof-select');
                this._close($firstNestedMenu);
                this._open($clickedNestedMenu);
                if (el === this.firstLvlSubmenu) {
                    //$(el).find(' > ul > li').removeClass('selected');
                    this._close($lastNestedMenu);
                }
                if (this.options.openCallback) {
                    return this.options.openCallback($(clickedEl).parent());
                }
            };

            Menutree.prototype._selectTopmenu = function(el, clickedEl) {
                var $firstNestedMenu;
                $firstNestedMenu = $(el).find(' > ul');
                $(el).removeClass('roof-select');
                $(el).removeClass('root-pandect');
                $(clickedEl).parent().addClass('root-pandect');
                this._close($firstNestedMenu);
                if (this.options.openCallback) {
                    return this.options.openCallback($(clickedEl).parent());
                }
            };

            Menutree.prototype._closeSubmenu = function(el) {
                var $clickedNestedMenu;
                $clickedNestedMenu = $(el).next('ul');
                $(el).parent().removeClass('roof-select');
                $(el).parent().removeClass('root-pandect');
                this._close($clickedNestedMenu);
                if (this.options.closeCallback) {
                    return this.options.closeCallback($(el).parent());
                }
            };

            Menutree.prototype._open = function($el) {
                $el.parent().find(' > div > img').attr('src', 'img-new/arrow-2.png');
                if (this.options.animate) {
                    return $el.slideDown(this.options.speed);
                } else {
                    return $el.show();
                }
            };

            Menutree.prototype._close = function($el) {
                $el.parent().find(' > div > img').attr('src', 'img-new/arrow-1.png');
                if (this.options.animate) {
                    return $el.slideUp(this.options.speed);
                } else {
                    return $el.hide();
                }
            };

            Menutree.prototype._hasChildenAndIsHidden = function(clickedE) {
                return $(clickedE).next('ul').length > 0 && $(clickedE).next('ul').is(':hidden');
            };

            Menutree.prototype._isCurrentlyOpen = function(el) {
                return $(el).parent().hasClass('roof-select') || $(el).parent().hasClass('root-pandect');
            };

            Menutree.prototype._hideSubmenus = function() {
                return $("" + this.firstLvlSubmenu + " > ul, " + this.secondLvlSubmenu + " > ul").hide();
                //$("" + this.firstLvlSubmenu + " > ul").removeClass('selected');
            };

            Menutree.prototype._showSubmenus = function() {
                return $("" + this.firstLvlSubmenu + " > ul, " + this.secondLvlSubmenu + " > ul").show();
                //$("" + this.firstLvlSubmenu).removeClass('selected');
            };

            Menutree.prototype._checkOptions = function() {
                if (this.options.animate !== true || false) {
                    console.warn("jQuery.fn.Menutree - '" + this.options.animate
                            + "' is not a valid parameter for the 'animate' option. Falling back to default value.");
                }
                if (this.options.speed !== parseInt(this.options.speed)) {
                    return console.warn("jQuery.fn.Menutree - '" + this.options.speed
                            + "' is not a valid parameter for the 'speed' option. Falling back to default value.");
                }
            };

            Menutree.prototype.destroy = function() {
                this.$el.removeData('menutree');
                this._unbindEvents();
                this._showSubmenus();
                this.$el.removeClass('menutree');
                this.$el.find('.root-pandect').removeClass('root-pandect');
                return this.$el.find('.roof-select').removeClass('roof-select');
            };

            Menutree.prototype.hideAll = function() {
                return this._hideSubmenus();
            };

            Menutree.prototype.showAll = function() {
                return this._showSubmenus();
            };

            return Menutree;

        })();
        return $.fn.extend({
            menutree : function() {
                var args, option;
                option = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
                return this.each(function() {
                    var $this, data;
                    $this = $(this);
                    data = $this.data('menutree');
                    if (!data) {
                        $this.data('menutree', (data = new Menutree(this, option)));
                    }
                    if (typeof option === 'string') {
                        return data[option].apply(data, args);
                    }
                });
            }
        });
    })(window.jQuery, window);

}).call(this);
