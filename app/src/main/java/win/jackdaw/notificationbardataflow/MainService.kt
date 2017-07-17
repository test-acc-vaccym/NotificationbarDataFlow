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
        val data_state = tm.dataState
        tile.run {
            icon = Icon.createWithResource(applicationContext, if (data_state == 0) R.drawable.data_enable else R.drawable.data_enable)
            state = if (data_state == 0) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
            updateTile()
        }
    }

    override fun onClick() {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val tile: Tile = qsTile
        val data_state = tm.dataState
        tile.run {
            icon = Icon.createWithResource(applicationContext, if (data_state == 0) R.drawable.data_enable else R.drawable.data_enable)
            state = if (data_state == 0) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            updateTile()
        }
        ShellUtils.execCommand(if (data_state == 0) "svc data enable" else "svc data disable", true)
    }

    override fun onStartListening() {
        super.onStartListening()
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val tile: Tile = qsTile
        val data_state = tm.dataState
        tile.run {
            icon = Icon.createWithResource(applicationContext, if (data_state == 0) R.drawable.data_enable else R.drawable.data_enable)
            state = if (data_state == 0) Tile.STATE_INACTIVE else Tile.STATE_ACTIVE
            updateTile()
        }
    }
}