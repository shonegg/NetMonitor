package shone.netmonitor;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.net.framework.NetMonitor;
import com.net.framework.NetObserver;

/**
 * Author   Shone
 * Date     23/07/16.
 * Github   https://github.com/shonegg
 */
public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private NetObserver mNetObserver = new NetObserver() {
        @Override
        public void notify(NetAction action) {
            if (action.isAvailable()) {
                Toast.makeText(mContext, "网络连接 wifi:" + action.isWifi(), Toast.LENGTH_SHORT).show();
                Log.e(MainActivity.class.getSimpleName(), "网络可用 > " + "网络类型:" + action.getType().toString());
            } else {
                Toast.makeText(mContext, "网络断开", Toast.LENGTH_SHORT).show();
                Log.e(MainActivity.class.getSimpleName(), "网络不可用 > " + "网络类型:" + action.getType().toString());
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

}
