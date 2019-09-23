package com.elmenus.elmenustask.menustags.data

import com.google.gson.annotations.SerializedName

class TagResponse(@SerializedName("tags") val tags: List<TagObject>)