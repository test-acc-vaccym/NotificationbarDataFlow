package win.jackdaw.notificationbardataflow

import android.content.Context
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
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        qsTile.label = tm.simOperatorName
        if (tm.dataState == 0) {
            qsTile.state = Tile.STATE_ACTIVE
        } else {
            qsTile.state = Tile.STATE_INACTIVE
        }
        qsTile.updateTile()
    }

    override fun onClick() {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (tm.dataState == 0) {
            ShellUtils.execCommand("svc data enable", true)
            qsTile.state = Tile.STATE_INACTIVE
        } else {
            ShellUtils.execCommand("svc data disable", true)
            qsTile.state = Tile.STATE_ACTIVE
        }
        qsTile.updateTile()
    }

    override fun onStartListening() {
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (tm.dataState == 0) {
            qsTile.state = Tile.STATE_ACTIVE
        } else {
            qsTile.state = Tile.STATE_INACTIVE
        }
        qsTile.updateTile()
    }

    override fun onStopListening() {
        super.onStopListening()
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
    }
}