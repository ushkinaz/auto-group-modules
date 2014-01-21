What is this?
=============

This is an IntelliJ Idea plugin for [live-plugin](https://github.com/dkandalov/live-plugin) plugin. It can:
 - automatically assign groups to modules, based on their meta information
 - nothing else

Why bother?
=============
If your project, consists of dozens of modules, you probaby want that list of modules to get organized. 
In many cases, naming of a module reflects its purpose. "money-tranformers" should go to "transformers" category.
IntelliJ Idea has a notion of module groups, which is supposed to provide this categorization. unfortunately, 
if you're using Maven and frequently changing branches, groups assigned to modules are often lost, and you end up with old plain modules structure.
This plugin aims to solve the issue by automagically assigning groups to modules, based on their names and som extra info.



How to install?
===============
 - install [live-plugin](https://github.com/dkandalov/live-plugin) plugin
 - in "Plugins" toolwindow choose "Add Plugin -> Plugin from Git" and use this repository address
 - in "Plugins" toolwindow run "auto-group-modules" plugin


How to configure?
===============
In general, there are two set of rules - matching by name regexp, and matching by packaging type from pom.xml, if available. Packaging matching takes precedence.
Rules are defined in Configuration.groovy, it should be self explanatory. I hope.
That file should be tailored for your project.
