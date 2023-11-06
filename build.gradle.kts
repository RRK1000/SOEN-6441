plugins {
    id("java")
}

group = "com.u2"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jgrapht:jgrapht-core:1.4.0")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(group="commons-cli", name= "commons-cli", version= "1.4")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Javadoc> {
    options {
        this as StandardJavadocDocletOptions
        addStringOption("Xdoclint:missing")
        addStringOption("Xwerror", "-quiet")
    }
}
