
(function (window) {
    function Table(jo, opt) {
        this.$element = jo;
        this.defaults = {
            url: null,
            pageSize: 8,//当前页一共最多多少行
            afterDraw: function () {

            }
        };
        this.options = $.extend({}, this.defaults, opt)
    }

    /*
     * 添加一行
     */
    Table.prototype.addRow = function (columns, callback) {
        var totalPages = this.simplePagination.pagination('getPagesCount');
        var $element = this.$element;
        this.selectPage(totalPages, function () {
            var rowTem = '<tr>';
            for (var i in columns) {
                rowTem += '<td>' + columns[i] + '</td>';
            }
            rowTem += '</tr>';

            $element.find('tbody').append(rowTem);
            callback()
        });
        return this;
    };

    /*
     * 取消添加
     */
    Table.prototype.deleterow = function () {
        var totalPages = this.simplePagination.pagination('getPagesCount');
        this.simplePagination.pagination('selectPage', totalPages)
    };

    /*
     * 页面跳转
     */
    Table.prototype.selectPage = function (page, callback) {
        this.get(parseInt(page), callback);
        this.simplePagination.pagination('drawPage', page);
        return this
    };

    /*
     * 构建表体
     */
    Table.prototype.draw = function (list, page) {
        this.clearTBody();
        var pageIndex = (page - 1) * this.options.pageSize
        var tbody = this.$element.find('table>tbody');
        var columns = this.options.columns;
        for (var i in list) {
            var item = list[i]
            tbody.append('<tr></tr>')
            var tr = tbody.children('tr:last')
            for (var j in columns) {
                var column = columns[j];
                if (typeof column.increment == 'number') {
                    var index = parseInt(pageIndex) + parseInt(i) + column.increment
                    tr.append('<td>' + index + '</td>')
                } else if (typeof column.data == 'string') {
                    var attr = column.data.split('.')
                    var data = item
                    for (var k in attr) {
                        if (data != null)
                            data = data[attr[k]]
                    }
                    if (data == null || data == "undefined") {
                        data = "--"
                    }
                    tr.append('<td>' + data + '</td>')
                } else if (typeof column.element == 'string') {
                    tr.append('<td>' + column.element + '</td>')
                } else {
                    tr.append('<td></td>')
                }
                if (typeof column.bool == 'object') {
                    var TRUE = column.bool.TRUE
                    var FALSE = column.bool.FALSE
                    var td = tr.children('td:last')
                    var data = td.text();
                    if (data == 'true' || data == '0') {
                        td.text(TRUE)
                    } else if (data == 'false' || data == '1') {
                        td.text(FALSE)
                    }
                }
                if (typeof column.number == 'object') {
					var ZERO = column.number.ZERO
					var ONE = column.number.ONE
					var TWO = column.number.TWO
					var THREE = column.number.THREE	
					var FOUR = column.number.FOUR
					var td = tr.children('td:last')
					var data = td.text();
					if (data == '0') {
						td.text(ZERO)
					}else if (data == '1') {
						td.text(ONE)
					}else if (data == '2') {
						td.text(TWO)
					}else if (data == '3') {
						td.text(THREE)
					}else if (data == '4') {
						td.text(FOUR)
					}
				}
            }
        }
        this.options.afterDraw();
        return this
    }

    /*
     * 从服务器获取数据
     */
    Table.prototype.get = function (page) {
        var thatArgs = arguments
        if (this.options.url != null) {
            $.ajax({
                url: this.options.url,
                data: {
                    pageSize: this.options.pageSize,
                    page: page,
                },
                dataType: 'json',
                // async : false,
                context: this,
                success: function (data) {
                    if (data.total == 0 && data.type == "search") {
                        confirmNoIndent();
                    } else {

                        this.options.list = data.list;
                        this.draw(data.list, page);
                        for (var i in thatArgs) {
                            if (typeof thatArgs[i] == 'number')
                                continue;
                            if (thatArgs[i] == true) {
                                this.initPagination(data.total);
                            } else if (typeof thatArgs[i] == 'function') {
                                thatArgs[i]();
                            }
                        }
                    }
                }
            })
        }
        return this;
    }

    /*
 * 清理当前页显示
 */
    Table.prototype.clearTBody = function () {
        this.$element.find('tbody>tr').remove();
        return this;
    };

    /*
    清理插件
     */
    Table.prototype.destroy = function () {
        this.$element.children('table').empty();
        this.simplePagination.pagination('destroy');
        this.$element.children('div .page-nav').remove();
        this.$element.children('div .addon').remove()
    };

    /*
     * 初始化分页插件
     */
    Table.prototype.initPagination = function (total) {
        var that = this;
        this.total = total
        this.simplePagination = this.$element.find('div.page-nav').pagination(
            {
                items: total,
                itemsOnPage: this.options.pageSize,
                cssStyle: 'light-theme',
                onPageClick: function (pageNumber) {
                    that.get(pageNumber)
                },
                onInit: function () {
                    var pages = this.pages

                    var nav = that.$element.append('<div class="addon"><span>共' + this.pages + '页&nbsp;第</span>' + '<span><input class="page"/>页</span>'
                        + '<span><button class="jump">&nbsp;确认</button></span></div>');

                    nav.find('div.addon>span:eq(2)>button').click(function () {
                        var page = $(this).parent().prev().children('input').val()
                        if (0 < page && page < pages + 1) {
                            that.selectPage(page)
                        }
                    })
                }
            })
        return this;
    }



    /*
     * 构建表格头
     */
    Table.prototype.drawHead = function () {
        var table = this.$element.children('table').append('<thead><tr></tr></thead>')
        var columns = this.options.columns
        for (var i in columns) {
            var column = columns[i]
            var tr = table.find('thead>tr').append('<th>' + column.title + '</th>')
            tr.children('th:last').css('width', column.width)
        }
        table.append('<tbody></tbody>')
        table.after('<div class="page-nav"></div>')
        return this;
    }

    Table.prototype.init = function () {
        this.drawHead().get(1, true)
        return this
    }

    $.fn.table = function (opt) {
        var table = new Table(this, opt)
        return table.init();
    }

    function confirmNoIndent() {
        $.mbox({
            area: ["300px", "auto"], //弹框大小
            border: [0, .5, "#666"],
            title: "未查询到相应订单",
            dialog: {
                msg: "请检查订单编号或者电话号码或者技师编号是否正确",
                btns: 1,   //1: 只有一个按钮   2：两个按钮  3：没有按钮 提示框
                type: 3,   //1:对钩   2：问号  3：叹号
                btn: ["确认"],  //自定义按钮
                yes: function () {  //点击左侧按钮：成功
                    $("#searchNumber").val("").focus()
                    //window.close();

                },
                no: function () {

                }

            }
        })
    }

})(window)