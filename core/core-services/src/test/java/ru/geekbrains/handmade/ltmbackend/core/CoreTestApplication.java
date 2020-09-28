package ru.geekbrains.handmade.ltmbackend.core;

import ru.geekbrains.handmade.ltmbackend.core.configurations.MultimoduleSpringBootApplication;


// Intellij Idea scanning Spring Configuration fix
// Bugfigz for IDE complaint: "Could not autowire" but really do nothing, only remove warning
// https://stackoverflow.com/questions/26889970/intellij-incorrectly-saying-no-beans-of-type-found-for-autowired-repository#comment92967484_31891805
//@SpringBootApplication(scanBasePackages ="ru.geekbrains.handmade.ltmbackend")
@MultimoduleSpringBootApplication
public class CoreTestApplication {
}
