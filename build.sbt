import com.github.retronym.SbtOneJar._

oneJarSettings

name := "graph-goliath"

version := "1.0"

scalaVersion := "2.12.1"

ideaExcludeFolders += ".idea"

ideaExcludeFolders += ".idea_modules"

mainClass in oneJar := Some("Main")

artifact in oneJar <<= moduleName(Artifact(_))

artifact in oneJar ~= { (art: Artifact) =>
  art.copy(`type` = "jar", extension = "jar", name = "gg")
}

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    