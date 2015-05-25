package com.apps.kawaii.helpme.Activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;

import com.apps.kawaii.helpme.Fragments.BlankFragment;
import com.apps.kawaii.helpme.Fragments.CustomMapFragment;
import com.apps.kawaii.helpme.Fragments.HelpCenterFragment;
import com.apps.kawaii.helpme.Fragments.MapContainerFragment;
import com.apps.kawaii.helpme.Fragments.MapFragment;
import com.apps.kawaii.helpme.Fragments.NotificationsFragment;
import com.apps.kawaii.helpme.InternalSystemClasses.HelpApplication;
import com.apps.kawaii.helpme.Models.User;
import com.apps.kawaii.helpme.R;
import com.apps.kawaii.helpme.Fragments.UserProfileFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.util.concurrent.ExecutionException;

import it.neokree.googlenavigationdrawer.GAccount;
import it.neokree.googlenavigationdrawer.GAccountListener;
import it.neokree.googlenavigationdrawer.GoogleNavigationDrawer;


public class MainActivity  extends GoogleNavigationDrawer implements GAccountListener {

    @Override
    public void init(Bundle savedInstanceState) {
        User currentUser= HelpApplication.appUser;
        // add first account

        GAccount account = new GAccount(currentUser.username,currentUser.email,this.getResources().getDrawable(R.drawable.photo),this.getResources().getDrawable(R.drawable.bamboo));

        this.addAccount(account);
        // set listener
        this.setAccountListener(this);

        // add your sections
        this.addSection(this.newSection("Map",this.getResources().getDrawable(R.drawable.ic_map_grey), new MapContainerFragment()));
        this.addSection(this.newSection("Help Center", this.getResources().getDrawable(R.drawable.ic_help_center), HelpCenterFragment.newInstance(1)));
        this.addDivisor();
        this.addSection(this.newSection("Notifications", this.getResources().getDrawable(R.drawable.ic_notifications), NotificationsFragment.newInstance()));
        this.addSection(this.newSection("Settings", this.getResources().getDrawable(R.drawable.ic_settings_black_24dp), new BlankFragment()));
        Glide.with(this).load(currentUser.avatar).transform(new CircleTransform(this)).into((ImageView) findViewById(R.id.user_photo));
    }

    @Override
    public void onAccountOpening(GAccount gAccount) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, UserProfileFragment.newInstance(String.valueOf(HelpApplication.appUser.id))).commit();
    }
    public static class CircleTransform extends BitmapTransformation {
        public CircleTransform(Context context) {
            super(context);
        }

        @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override public String getId() {
            return getClass().getName();
        }
    }
}