object Libraries {
    object ScalaVersions {
        const val scalaVBase = "2.12"
        const val scalaVPatch = "11"
        const val scalaV = "$scalaVBase.$scalaVPatch"
    }

    private object Versions {
    }

    const val scalaLibrary = "org.scala-lang:scala-library:${ScalaVersions.scalaV}"
}

object TestLibraries {
    private object Versions {
        const val scalaTestV = "3.1.2"
    }

    const val scalaTest = "org.scalatest:scalatest_${Libraries.ScalaVersions.scalaVBase}:${Versions.scalaTestV}"
}