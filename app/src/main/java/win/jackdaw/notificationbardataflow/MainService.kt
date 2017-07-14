package win.jackdaw.notificationbardataflow

import android.content.Context
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.telephony.TelephonyManager
import win.jackdaw.notificationbardataflow.util.ShellUtils

/**
 * Created by zhuang on 2017/7/12
 */
class MainService : TileService() {

    init {
        ShellUtils.checkRootPermission()
    }

    override fun onTileAdded() {
        super.onTileAdded()
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val tile: Tile = qsTile
        if (tm.dataState == 0) {
            tile.icon = Icon.createWithResource(applicationContext, R.drawable.data_icon_disable)
        } else {
            tile.icon = Icon.createWithResource(applicationContext, R.drawable.data_icon)
        }
        tile.run {
            state = Tile.STATE_ACTIVE
            updateTile()
        }
    }

    override fun onClick() {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val tile: Tile = qsTile
        if (tm.dataState == 0) {
            tile.run {
                icon = Icon.createWithResource(applicationContext, R.drawable.data_icon)
                updateTile()
            }
            ShellUtils.execCommand("svc data enable", true)
        } else {
            tile.run {
                icon = Icon.createWithResource(applicationContext, R.drawable.data_icon_disable)
                updateTile()
            }
            ShellUtils.execCommand("svc data disable", true)
        }
    }

    override fun onStartListening() {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val tile: Tile = qsTile
        if (tm.dataState == 0) {
            tile.icon = Icon.createWithResource(applicationContext, R.drawable.data_icon_disable)
        } else {
            tile.icon = Icon.createWithResource(applicationContext, R.drawable.data_icon)
        }
        tile.run {
            state = Tile.STATE_ACTIVE
            updateTile()
        }
    }
}