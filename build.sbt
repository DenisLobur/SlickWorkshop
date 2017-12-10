val globalSettings = Seq[SettingsDefinition](
  version := "0.1",
  scalaVersion := "2.12.4"
)
// adding remote project
//lazy val depProject = ProjectRef(uri("https://github.com/tumblr/colossus.git@master"), "colossus")

val model = project.in(file("model"))
  .settings(globalSettings: _*)

val repositories = project.in(file("repositories"))
  .dependsOn(model)
  .settings(globalSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.2.1",
      "org.slf4j" % "slf4j-nop" % "1.6.4",
      "com.typesafe.slick" %% "slick-hikaricp" % "3.2.1",
      "org.postgresql" % "postgresql" % "42.1.4"
    )
  )

val application = project.in(file("application"))
  .dependsOn(repositories)
  .settings(globalSettings: _*)

val root = Project("slick_workshop", file("."))
    .aggregate(application)
