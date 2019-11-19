package com.gabrielmaz.soda.mocks

import android.content.Context
import com.gabrielmaz.soda.data.helper.networking.NetworkingManager

class NetworkingManagerMock(context: Context) : NetworkingManager(context) {

    var networkingAvailable = true

    override fun isNetworkOnline() = networkingAvailable

}