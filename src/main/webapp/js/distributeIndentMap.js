/**
 * Created by huanglin on 2017/7/25.
 */
//地图初始化时，在地图上添加一个marker标记,鼠标点击marker可弹出自定义的信息窗体
function distributeMap(conMap,longitude,latitude) {

    var map = new AMap.Map(conMap, {
        resizeEnable: true,
        center: [longitude, latitude],
        zoom: 16
    });
    addMarker();

//添加marker标记
    function addMarker() {
        map.clearMap();
        var marker = new AMap.Marker({
            map: map,
            position: [longitude, latitude]
        });
        //鼠标点击marker弹出自定义的信息窗体
        AMap.event.addListener(marker, 'click', function () {
            infoWindow.open(map, marker.getPosition());
        });
    }

//实例化信息窗体
    var title = '方恒假日酒店<span style="font-size:11px;color:#F00;">价格:318</span>',
        content = [];
    content.push("<img src='https://tpc.googlesyndication.com/simgad/5843493769827749134'>地址：北京市朝阳区阜通东大街6号院3号楼东北8.3公里");
    content.push("电话：010-64733333");
    content.push("<a href='https://ditu.amap.com/detail/B000A8URXB?citycode=110105'>详细信息</a>");
    var infoWindow = new AMap.InfoWindow({
        isCustom: true,  //使用自定义窗体
        content: createInfoWindow(title, content.join("<br/>")),
        offset: new AMap.Pixel(16, -45)
    });

//构建自定义信息窗体
    function createInfoWindow(title, content) {
        var info = document.createElement("div");
        info.className = "info";

        //可以通过下面的方式修改自定义窗体的宽高
        //info.style.width = "400px";
        // 定义顶部标题
        var top = document.createElement("div");
        var titleD = document.createElement("div");
        var closeX = document.createElement("img");
        top.className = "info-top";
        titleD.innerHTML = title;
        closeX.src = "https://webapi.amap.com/images/close2.gif";
        closeX.onclick = closeInfoWindow;

        top.appendChild(titleD);
        top.appendChild(closeX);
        info.appendChild(top);

        // 定义中部内容
        var middle = document.createElement("div");
        middle.className = "info-middle";
        middle.style.backgroundColor = 'white';
        middle.innerHTML = content;
        info.appendChild(middle);

        // 定义底部内容
        var bottom = document.createElement("div");
        bottom.className = "info-bottom";
        bottom.style.position = 'relative';
        bottom.style.top = '0px';
        bottom.style.margin = '0 auto';
        var sharp = document.createElement("img");
        sharp.src = "https://webapi.amap.com/images/sharp.png";
        bottom.appendChild(sharp);
        info.appendChild(bottom);
        return info;
    }

//关闭信息窗体
    function closeInfoWindow() {
        map.clearInfoWindow();
    }

    if (typeof map !== 'undefined') {
        map.on('complete', function() {
            if (location.href.indexOf('guide=1') !== -1) {
                map.setStatus({
                    scrollWheel: false
                });
                if (location.href.indexOf('litebar=0') === -1) {
                    map.plugin(["AMap.ToolBar"], function() {
                        var options = {
                            liteStyle: true
                        }
                        if (location.href.indexOf('litebar=1') !== -1) {
                            options.position = 'LT';
                            options.offset = new AMap.Pixel(10, 40);
                        } else if (location.href.indexOf('litebar=2') !== -1) {
                            options.position = 'RT';
                            options.offset = new AMap.Pixel(20, 40);
                        } else if (location.href.indexOf('litebar=3') !== -1) {
                            options.position = 'LB';
                        } else if (location.href.indexOf('litebar=4') !== -1) {
                            options.position = 'RB';
                        }
                        map.addControl(new AMap.ToolBar(options));
                    });
                }
            }
        });
    }


    var mock = {
        log: function (result) {
            window.parent.setIFrameResult('log', result);
        }
    }
    console = mock;
    window.Konsole = {
        exec: function (code) {
            code = code || '';
            try {
                var result = window.eval(code);
                window.parent.setIFrameResult('result', result);
            } catch (e) {
                window.parent.setIFrameResult('error', e);
            }
        }
    }

}



