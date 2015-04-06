package com.irene.test.tweetsnow.interfaces;

/**
 * Interface to replace the methods of asynctask
 *
 * Created by Irene on 28/03/2015.
 */

public interface AuxiliarAsyncFunction {
    /**
     * This method is executed after the method doInBackground
     *
     * @param result
     * @param output
     */
    public void OnPostExecute(Boolean result, Object output);

    /**
     * This method is executed before the method doInBackground
     */
    public void OnPreExecute();
}
