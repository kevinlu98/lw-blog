$(function () {
    editormd('lw-markdown-content', {
        height: 700,
        emoji: true,                 // 开启emoji支持
        tex: true,                   // 开启科学公式TeX语言支持，默认关闭
        flowChart: true,             // 开启流程图支持，默认关闭
        sequenceDiagram: true,       // 开启时序/序列图支持，默认关闭,
        searchReplace: true,
        toolbarAutoFixed: false,
        imageUpload: true,
        imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL: "/admin/md/upload",
        path: `${$('#lw-cdn-prefix').val()}/static/plugin/editormd/lib/`,
        toolbarIcons: [
            "undo", "redo", "|",
            "bold", "del", "italic", "quote", "|",
            "h1", "h2", "h3", "h4", "h5", "h6", "|",
            "list-ul", "list-ol", "hr", "|",
            "emoji", "link", "image", "code", "code-block", "table", "datetime", "|",
            "fw-message", "fw-alert", "fw-tab", 'fw-group', 'lw-linkbtn', 'lw-color-line', 'lw-music', 'lw-bilibili', "|",
            "watch", "preview", "fullscreen", "clear", "search"
        ],
        toolbarIconsClass: {
            'fw-alert': 'fa-comment',
            'fw-message': "fa-bell",
            'fw-tab': "fa-columns",
            'fw-group': 'fa-newspaper-o',
            'lw-linkbtn': 'fa-paper-plane',
            'lw-color-line': 'fa-arrows-h',
            'lw-music': 'fa-music',
            'lw-bilibili': 'fa-video-camera'
        },
        toolbarHandlers: {
            'fw-message': function (cm) {
                let content = `<div class="fw-layer-content" style="width: 300px;">
                                            <div class="fw-form-item">
                                            <label>提示类型：</label>
                                            <select id="fw-alert-type" style="width: 235px;">
                                            <option value="success">成功</option>
                                            <option value="error">失败</option>
                                            <option value="info">信息</option>
                                            <option value="warning">警告</option>
                                            </select>
                                            </div>
                                            <div class="fw-form-item"><label>提示内容：</label><textarea style="outline:none;width: 235px;resize: none;height: 100px;" id="fw-alert-content">${cm.getSelections()['0']}</textarea></div>
                                            </div>`
                let idx = layer.open({
                    type: 1,
                    width: 600,
                    title: '插入提示',
                    btn: ['确定', '取消'],
                    content: content,
                    btn1: () => {
                        cm.replaceSelection(`{fwalert type="${$("#fw-alert-type").val()}"}${$("#fw-alert-content").val()}{/fwalert}`);
                        cm.focus()
                        layer.close(idx)
                    }
                })
            },
            'fw-alert': function (cm, icon, cursor, selection) {
                let content = `<div class="fw-layer-content" style="width: 300px;">
                                            <div class="fw-form-item">
                                            <label>消息类型：</label>
                                            <select class="form-control" id="fw-msg-type"  style="width: 235px;">
                                            <option value="success">成功</option>
                                            <option value="error">失败</option>
                                            <option value="info">信息</option>
                                            <option value="warning">警告</option>
                                            </select>
                                            </div>
                                            <div class="fw-form-item"><label  style="vertical-align: top">提示内容：</label><textarea style="outline:none;width: 235px;resize: none;height: 100px;" id="fw-msg-content">${cm.getSelections()['0']}</textarea></div>
                                            </div>`
                let idx = layer.open({
                    type: 1,
                    width: 600,
                    title: '插入消息',
                    btn: ['确定', '取消'],
                    content: content,
                    btn1: () => {
                        cm.replaceSelection(`\n{fwcode type="${$("#fw-msg-type").val()}"}\n${$("#fw-msg-content").val()}\n{/fwcode}\n`);
                        cm.focus()
                        layer.close(idx)
                    }
                })
            },
            'fw-tab': function (cm) {
                let content = `<div class="fw-layer-content">
                                            <div class="fw-form-item">
                                            <label>tab栏列：</label>
                                            <input type="number" id="fw-tab-col" value="2">
                                            </div>
                                            </div>`
                let idx = layer.open({
                    type: 1,
                    width: 600,
                    title: '插入tab栏',
                    btn: ['确定', '取消'],
                    content: content,
                    btn1: () => {
                        let col = $("#fw-tab-col").val()
                        if (col < 2) {
                            layer.msg('请至少选择2列', {icon: 2})
                            return false;
                        }
                        col = col < 2 ? 2 : col;
                        let text = '\n{fwtab}\n{fwh}\n'
                        for (let i = 0; i < col; i++) {
                            text += `{fwthead target="${i + 1}"} tab栏${i + 1} {/fwthead}\n`
                        }
                        text += '{/fwh}\n{fwb}\n'
                        for (let i = 0; i < col; i++) {
                            text += `{fwtbody target="${i + 1}"}\n内容${i + 1}\n{/fwtbody}\n`
                        }
                        text += '{/fwb}\n{/fwtab}\n'
                        cm.replaceSelection(text);
                        cm.focus()
                        layer.close(idx)
                    }
                })
            },
            'fw-group': function (cm) {
                let content = `<div class="fw-layer-content" style="width: 300px;">
                                            <div class="fw-form-item">
                                            <label>分组名称：</label>
                                            <input type="text" id="fw-group-title" style="width: 235px;">
                                            </div>
                                            <div class="fw-form-item"><label style="vertical-align: top">分组内容：</label> <textarea style="width: 235px;outline: none;height: 100px;" id="fw-group-cn">${cm.getSelections()[0]}</textarea></div>
                                            </div>`
                let idx = layer.open({
                    type: 1,
                    width: 600,
                    title: '插入分组卡片',
                    btn: ['确定', '取消'],
                    content: content,
                    btn1: () => {
                        let btncontent = `\n{fwgroup title="${$("#fw-group-title").val()}"}\n${$("#fw-group-cn").val()}\n{/fwgroup}\n`
                        cm.replaceSelection(btncontent);
                        cm.focus()
                        layer.close(idx)
                    }
                })
            },
            'lw-linkbtn': function (cm) {
                let content = `<div class="fw-layer-content" style="width: 300px;">
                                            <div class="fw-form-item">
                                            <label>按钮类型：</label>
                                            <select id="fw-btn-type" style="width: 235px;">
                                            <option value="normal">正常</option>
                                            <option value="success">成功</option>
                                            <option value="error">失败</option>
                                            <option value="info">信息</option>
                                            <option value="warning">警告</option>
                                            </select>
                                            </div>
                                            <div class="fw-form-item"><label>按钮内容：</label> <input type="text" style="width: 235px;" value="${cm.getSelections()[0]}" id="fw-btn-text"></div>
                                            <div class="fw-form-item"><label>按钮图标：</label> <input type="text" style="width: 235px;"  id="fw-btn-icon" placeholder="不填写默认为下载图标"></div>
                                            <div class="fw-form-item"><label>跳转链接：</label> <input type="text" style="width: 235px;"  id="fw-btn-url"></div>
                                            <p style="margin: 0;padding: 0;font-size: 12px;color: #777;">图标为 <a href="https://www.thinkcmf.com/font/font_awesome/icons.html" target="_blank">fontawesome字体图标</a>，拷贝图标名称即可</p>
                                            </div>`
                let idx = layer.open({
                    type: 1,
                    width: 600,
                    title: '插入跳转按钮',
                    btn: ['确定', '取消'],
                    content: content,
                    btn1: () => {
                        let url = $("#fw-btn-url").val();
                        url = url ? url : '#'
                        let icon = $("#fw-btn-icon").val();
                        icon = icon ? icon : 'fa-download';
                        let btncontent = `{fwbtn type="${$("#fw-btn-type").val()}" url="${url.replace('/', '\\/')}"}{icon="${icon}"}${$("#fw-btn-text").val()}{/fwbtn}`
                        cm.replaceSelection(btncontent);
                        cm.focus()
                        layer.close(idx)
                    }
                })
            },
            'lw-color-line': function (cm) {
                let content = `<div class="fw-layer-content" style="width: 300px">
                                                <div class="fw-form-item">
                                                <label>开始颜色：</label>
                                                <input id="color-start" value="#01D0FF" style="height: 26px;" >
                                                <div id="start-btn" style="border: 1px solid #000; display: inline-block;vertical-align: bottom"></div>
                                                </div>
                                                <div class="fw-form-item">
                                                <label>结束颜色：</label>
                                                <input id="color-end"  value="#FC3E85" style="height: 26px;">
                                                <div id="end-btn" style="border: 1px solid #000; display: inline-block;vertical-align: bottom"></div>
                                                </div>
                                                </div>`
                let idx = layer.open({
                    type: 1,
                    width: 600,
                    title: '插入彩色分割符',
                    btn: ['确定', '取消'],
                    content: content,
                    btn1: () => {
                        let btncontent = `\n{fwcline start="${$("#color-start").val()}" end="${$("#color-end").val()}"}{/fwcline}\n`
                        cm.replaceSelection(btncontent);
                        cm.focus()
                        layer.close(idx)
                    }
                })
                new XNColorPicker({
                    color: "#01D0FF",
                    selector: "#start-btn",
                    showprecolor: true,//显示预制颜色
                    prevcolors: null,//预制颜色，不设置则默认
                    showhistorycolor: true,//显示历史
                    historycolornum: 16,//历史条数
                    format: 'hex',//rgba hex hsla,初始颜色类型
                    showPalette: false,//显示色盘
                    show: false, //初始化显示
                    lang: 'cn',// cn 、en
                    colorTypeOption: 'single,linear-gradient,radial-gradient',//
                    onError: function (e) {

                    },
                    onCancel: function (color) {

                    },
                    onChange: function (color) {
                        $('#color-start').val(color.color.hex).css('color', color.color.hex)
                    },
                    onConfirm: function (color) {
                        $('#color-start').val(color.color.hex)
                    }
                })
                new XNColorPicker({
                    color: "#FC3E85",
                    selector: "#end-btn",
                    showprecolor: true,//显示预制颜色
                    prevcolors: null,//预制颜色，不设置则默认
                    showhistorycolor: true,//显示历史
                    historycolornum: 16,//历史条数
                    format: 'hex',//rgba hex hsla,初始颜色类型
                    showPalette: false,//显示色盘
                    show: false, //初始化显示
                    lang: 'cn',// cn 、en
                    colorTypeOption: 'single,linear-gradient,radial-gradient',//
                    onError: function (e) {

                    },
                    onCancel: function (color) {

                    },
                    onChange: function (color) {
                        $('#color-end').val(color.color.hex).css('color', color.color.hex)
                    },
                    onConfirm: function (color) {
                        $('#color-end').val(color.color.hex)
                    }
                })

            },
            'lw-music': function (cm) {
                let content = `<div class="fw-layer-content" style="width: 300px;">
                                            <div class="fw-form-item">
                                            <label>音乐来源：</label>
                                            <select id="music-source" style="width: 220px;">
                                            <option value="netease" selected>网易云音乐</option>
                                            <option value="tencent">腾讯音乐</option>
                                            </select>
                                            </div>
                                            <div class="fw-form-item">
                                            <label>音乐类型：</label>
                                            <select id="music-type" style="width: 220px;">
                                            <option value="playlist" selected>歌单</option>
                                            <option value="song">歌曲</option>
                                            </select>
                                            </div>
                                            <div class="fw-form-item">
                                            <label>音乐 &nbsp;I D：</label>
                                            <input id="music-id" placeholder="歌曲ID/歌单ID" style="width: 220px;">
                                            </div>
                                            </div>`
                let idx = layer.open({
                    type: 1,
                    width: 600,
                    title: '插入音乐',
                    btn: ['确定', '取消'],
                    content: content,
                    btn1: () => {
                        let ms = $("#music-source").val()
                        let mt = $("#music-type").val()
                        let mi = $("#music-id").val()
                        let btncontent = `\n{fwmusic source="${ms}" type="${mt}" id="${mi}"}{/fwmusic}\n`
                        cm.replaceSelection(btncontent);
                        cm.focus()
                        layer.close(idx)
                    }
                })
            },
            'lw-bilibili': function (cm) {
                let content = `<div class="fw-layer-content" style="width: 300px;">
                                            <div class="fw-form-item">
                                            <label>视频BVID：</label>
                                            <input type="text" id="fw-bvid" style="width: 220px;">
                                            </div>
                                            <div class="fw-form-item">
                                            <label>视频剧集：</label>
                                            <input type="number" id="fw-bvnu" value="1" style="width: 220px;">
                                            </div>
                                            </div>`
                let idx = layer.open({
                    type: 1,
                    width: 600,
                    title: '插入B站视频',
                    btn: ['确定', '取消'],
                    content: content,
                    btn1: () => {
                        let bvnu = $("#fw-bvnu").val() ? $("input#fw-bvnu").val() : 1;
                        let btncontent = `\n{fwbili bvid="${$("#fw-bvid").val()}" bvnu="${bvnu}"}{/fwbili}\n`
                        cm.replaceSelection(btncontent);
                        cm.focus()
                        layer.close(idx)
                    }
                })
            }
        },
        onload: function () {
            $('.editormd-preview .editormd-preview-container').attr('id', 'lw-article-content')
            $('.editormd-preview-close-btn').hide()
            $("#fw-article-content").on('click', '.fwh .fwthead', function () {
                $(this).parent().children('.fwthead').removeClass('fwcurrent')
                $(this).addClass('fwcurrent')
                $(this).parent().parent().find('.fwtbody').hide()
                $(this).parent().parent().find(`.fwtbody-${$(this).data('target')}`).stop().fadeIn()
            })

        },
        onfullscreen: function () {
            this.editor.css('zIndex', 999)
        }
    })
})