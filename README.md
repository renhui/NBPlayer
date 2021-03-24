# NBPlayer
NBPlayer：一个能播放播放无B帧视频流的播放器

#### 说明：此NB非彼NB，而是NO B Frame。即本播放器用于播放不带有B帧的视频流。


#### 用途：播放不带B帧的视频，那么最能适配的需求场景莫过于直播流，尤其是直播拉流。

#### 

> TODO：采用OpengGL绘制，提升性能


#### 运行示例图：
![avatar](https://github.com/renhui/NBPlayer/blob/master/NBPlayer/Screenshot.png)



#### 补充  - 直播测试源：

CCTV-1综合:rtmp://58.200.131.2:1935/livetv/cctv1
CCTV-2财经:rtmp://58.200.131.2:1935/livetv/cctv2
CCTV-3综艺:rtmp://58.200.131.2:1935/livetv/cctv3
CCTV-4中文国际:rtmp://58.200.131.2:1935/livetv/cctv4
CCTV-5体育:rtmp://58.200.131.2:1935/livetv/cctv5
CCTV-6电影:rtmp://58.200.131.2:1935/livetv/cctv6
CCTV-7军事农业:rtmp://58.200.131.2:1935/livetv/cctv7
CCTV-8电视剧:rtmp://58.200.131.2:1935/livetv/cctv8
CCTV-9记录:rtmp://58.200.131.2:1935/livetv/cctv9
CCTV-10科教:rtmp://58.200.131.2:1935/livetv/cctv10
CCTV-11戏曲:rtmp://58.200.131.2:1935/livetv/cctv11
CCTV-12社会与法:rtmp://58.200.131.2:1935/livetv/cctv12
CCTV-13新闻:rtmp://58.200.131.2:1935/livetv/cctv13
CCTV-14少儿:rtmp://58.200.131.2:1935/livetv/cctv14
CCTV-15音乐:rtmp://58.200.131.2:1935/livetv/cctv15
安徽卫视:rtmp://58.200.131.2:1935/livetv/ahtv
兵团卫视:rtmp://58.200.131.2:1935/livetv/bttv
重庆卫视:rtmp://58.200.131.2:1935/livetv/cqtv
东方卫视:rtmp://58.200.131.2:1935/livetv/dftv
东南卫视:rtmp://58.200.131.2:1935/livetv/dntv
广东卫视:rtmp://58.200.131.2:1935/livetv/gdtv
广西卫视:rtmp://58.200.131.2:1935/livetv/gxtv
甘肃卫视:rtmp://58.200.131.2:1935/livetv/gstv
贵州卫视:rtmp://58.200.131.2:1935/livetv/gztv
湖北卫视:rtmp://58.200.131.2:1935/livetv/hbtv
湖南卫视:rtmp://58.200.131.2:1935/livetv/hunantv
河北卫视:rtmp://58.200.131.2:1935/livetv/hebtv
河南卫视:rtmp://58.200.131.2:1935/livetv/hntv
黑龙江卫视:rtmp://58.200.131.2:1935/livetv/hljtv
江苏卫视:rtmp://58.200.131.2:1935/livetv/jstv
江西卫视:rtmp://58.200.131.2:1935/livetv/jxtv
吉林卫视:rtmp://58.200.131.2:1935/livetv/jltv
辽宁卫视:rtmp://58.200.131.2:1935/livetv/lntv
内蒙古卫视:rtmp://58.200.131.2:1935/livetv/nmtv
宁夏卫视:rtmp://58.200.131.2:1935/livetv/nxtv
青海卫视:rtmp://58.200.131.2:1935/livetv/qhtv
四川卫视:rtmp://58.200.131.2:1935/livetv/sctv
山东卫视:rtmp://58.200.131.2:1935/livetv/sdtv
山西卫视:rtmp://58.200.131.2:1935/livetv/sxrtv
陕西卫视:rtmp://58.200.131.2:1935/livetv/sxtv
山东教育:rtmp://58.200.131.2:1935/livetv/sdetv
中国教育-1:rtmp://58.200.131.2:1935/livetv/cetv1
中国教育-3:rtmp://58.200.131.2:1935/livetv/cetv3
中国教育-4:rtmp://58.200.131.2:1935/livetv/cetv4
CCTV-第一剧场:rtmp://58.200.131.2:1935/livetv/dyjctv
CCTV-国防军事:rtmp://58.200.131.2:1935/livetv/gfjstv
CCTV-怀旧剧场:rtmp://58.200.131.2:1935/livetv/hjjctv
CCTV-风云剧场:rtmp://58.200.131.2:1935/livetv/fyjctv
CCTV-风云足球:rtmp://58.200.131.2:1935/livetv/fyzqtv
CCTV-风云音乐:rtmp://58.200.131.2:1935/livetv/fyyytv
CCTV-世界地理:rtmp://58.200.131.2:1935/livetv/sjdltv
CCTV-1HD:rtmp://58.200.131.2:1935/livetv/cctv1hd
CCTV-2HD:rtmp://58.200.131.2:1935/livetv/cctv2hd
CCTV-3HD:rtmp://58.200.131.2:1935/livetv/cctv3hd
CCTV-4HD:rtmp://58.200.131.2:1935/livetv/cctv4hd
CCTV-5HD:rtmp://58.200.131.2:1935/livetv/cctv5hd
CCTV5+HD:rtmp://58.200.131.2:1935/livetv/cctv5phd
CCTV-6HD:rtmp://58.200.131.2:1935/livetv/cctv6hd
CCTV-7HD:rtmp://58.200.131.2:1935/livetv/cctv7hd
CCTV-8HD:rtmp://58.200.131.2:1935/livetv/cctv8hd
CCTV-9HD:rtmp://58.200.131.2:1935/livetv/cctv9hd
CCTV-10HD:rtmp://58.200.131.2:1935/livetv/cctv10hd
CCTV-12HD:rtmp://58.200.131.2:1935/livetv/cctv12hd
CCTV-14HD:rtmp://58.200.131.2:1935/livetv/cctv14hd
CGTN-新闻:rtmp://58.200.131.2:1935/livetv/cctv16
CETV-1:rtmp://58.200.131.2:1935/livetv/cetv1
CETV-3:rtmp://58.200.131.2:1935/livetv/cetv3
CETV-4:rtmp://58.200.131.2:1935/livetv/cetv4
北京卫视高清:rtmp://58.200.131.2:1935/livetv/btv1hd
北京影视高清:rtmp://58.200.131.2:1935/livetv/btv4hd
北京体育高清:rtmp://58.200.131.2:1935/livetv/btv6hd
北京新闻高清:rtmp://58.200.131.2:1935/livetv/btv9hd
北京纪实高清:rtmp://58.200.131.2:1935/livetv/btv11hd
北京卫视:rtmp://58.200.131.2:1935/livetv/btv1
北京文艺:rtmp://58.200.131.2:1935/livetv/btv2
北京科教:rtmp://58.200.131.2:1935/livetv/btv3
北京影视:rtmp://58.200.131.2:1935/livetv/btv4
北京财经:rtmp://58.200.131.2:1935/livetv/btv5
北京体育:rtmp://58.200.131.2:1935/livetv/btv6
北京生活:rtmp://58.200.131.2:1935/livetv/btv7
北京青年:rtmp://58.200.131.2:1935/livetv/btv8
北京新闻:rtmp://58.200.131.2:1935/livetv/btv9
北京卡酷:rtmp://58.200.131.2:1935/livetv/btv10
北京文艺高清:rtmp://58.200.131.2:1935/livetv/btv2hd
安徽卫视高清:rtmp://58.200.131.2:1935/livetv/ahhd
重庆卫视高清:rtmp://58.200.131.2:1935/livetv/cqhd
东方卫视高清:rtmp://58.200.131.2:1935/livetv/dfhd
天津卫视高清:rtmp://58.200.131.2:1935/livetv/tjhd
东南卫视高清:rtmp://58.200.131.2:1935/livetv/dnhd
江西卫视高清:rtmp://58.200.131.2:1935/livetv/jxhd
河北卫视高清:rtmp://58.200.131.2:1935/livetv/hebhd
湖南卫视高清:rtmp://58.200.131.2:1935/livetv/hunanhd
湖北卫视高清:rtmp://58.200.131.2:1935/livetv/hbhd
辽宁卫视高清:rtmp://58.200.131.2:1935/livetv/lnhd
四川卫视高清:rtmp://58.200.131.2:1935/livetv/schd
江苏卫视高清:rtmp://58.200.131.2:1935/livetv/jshd
浙江卫视高清:rtmp://58.200.131.2:1935/livetv/zjhd
山东卫视高清:rtmp://58.200.131.2:1935/livetv/sdhd
广东卫视高清:rtmp://58.200.131.2:1935/livetv/gdhd
深圳卫视高清:rtmp://58.200.131.2:1935/livetv/szhd
黑龙江卫视高清:rtmp://58.200.131.2:1935/livetv/hljhd
CHC高清电影:rtmp://58.200.131.2:1935/livetv/chchd
上海纪实高清:rtmp://58.200.131.2:1935/livetv/docuchina
金鹰纪实高清:rtmp://58.200.131.2:1935/livetv/gedocu
全纪实高清:rtmp://58.200.131.2:1935/livetv/documentaryhd
凤凰卫视中文台:rtmp://58.200.131.2:1935/livetv/fhzw
凤凰卫视资讯台:rtmp://58.200.131.2:1935/livetv/fhzx
凤凰卫视电影台:rtmp://58.200.131.2:1935/livetv/fhdy
星空卫视:rtmp://58.200.131.2:1935/livetv/startv
Star Sports:rtmp://58.200.131.2:1935/livetv/starsports
Channel[V]:rtmp://58.200.131.2:1935/livetv/channelv
探索频道:rtmp://58.200.131.2:1935/livetv/discovery
国家地理频道:rtmp://58.200.131.2:1935/livetv/natlgeo
CHC家庭影院:rtmp://58.200.131.2:1935/livetv/chctv
CHC动作电影:rtmp://58.200.131.2:1935/livetv/chcatv
美国电视频道:rtmp://media3.scctv.net/live/scctv_800
香港财经:rtmp://202.69.69.180:443/webcast/bshdlive-pc 

