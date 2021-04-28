在gradle.properties中添加

org.gradle.java.home=your jdk path
注意:此种方法没有尝试过，不确定是否真的有效

如果你是直接通过命令行执行任务,比如./gradlew build等，我使用第二种方法出现了乱码，编译不通过，也懒得去深究了，可以使用

gradle build -Dorg.gradle.java.home='your jdk path'
命令行带参数的方式执行