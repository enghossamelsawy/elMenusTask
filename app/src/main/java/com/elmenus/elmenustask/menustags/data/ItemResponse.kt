package com.elmenus.elmenustask.menustags.data

import com.google.gson.annotations.SerializedName

class ItemResponse(@SerializedName("items") var itemResponse: List<ItemOfTags>)