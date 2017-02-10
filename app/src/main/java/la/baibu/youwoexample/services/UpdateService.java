package la.baibu.youwoexample.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by minna_Zhou on 2017/1/17 0017.
 */
public class UpdateService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UpdateService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
