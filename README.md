这是一个运用观察者模式写的网络状态监听库!

注意在清单文件中添加网络权限:
< uses-permission   android:name="android.permission.INTERNET"  /><br/>< uses-permission   android:name="android.permission.ACCESS_WIFI_STATE" /> <br/>< uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

**用法** <br/>
1-继承NetApplication,或者在AndroidManifest.xml中声明(套路)
 
```  
    public class App extends NetApplication {
     @Override
     public void onCreate() {
          super.onCreate();
     }
}
```  

2-如果Activity需要监听网络状态

```  
    private NetObserver mNetObserver = new NetObserver() {
        @Override
        public void notify(NetAction action) {
            if (action.isAvailable()) {
            } else {
            }
        }
    };
   @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        NetMonitor.getInstance().addObserver(this.mNetObserver);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetMonitor.getInstance().delObserver(this.mNetObserver);
    }
```

PS:
NetApplication主要是内部初始化广播监听者,如果广播接收者收到了网络变化广播,就会解析当前具体网络类型,然后通知被观察者(Observable),被观察者通知所有观察者网络有变化了和当前网络类型,这里枚举了6种类型:
UNKNOWN, WIFI, MOBILE, MOBILE2G, MOBILE3G, MOBILE4G
