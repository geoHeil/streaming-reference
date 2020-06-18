// Copyright (C) 0 geoHeil
package com.github.geoheil.streamingreference

import java.io.File

operator fun File.div(s: String) = File(this, s)
