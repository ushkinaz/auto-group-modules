import com.intellij.notification.NotificationType
import com.intellij.openapi.module.ModuleManager
import com.intellij.psi.search.FilenameIndex

import static liveplugin.PluginUtil.runWriteAction
import static liveplugin.PluginUtil.show

/* ==========================================
 * All configuration is done in Configuration.groovy
 * ==========================================
 */

conf = new Configuration()

runWriteAction {
    mm = ModuleManager.getInstance(project)
    modifiableModel = mm.getModifiableModel()
    modules = modifiableModel.getModules()

    for (module in modules) {
        def moduleInfo = extractMavenInfo(module)

        newGroup = conf.packagingRules.findResult {
            if (moduleInfo?.packaging?.matches(it.key)) {
                return it.value
            }
            return null
        }

        if (newGroup == null) {
            newGroup = conf.nameRules.findResult {
                if (module.name.matches(it.key)) {
                    return it.value
                }
                return null
            }
        }
        if (newGroup == null) {
            newGroup = conf.DEFAULT_GROUP
        }
        modifiableModel.setModuleGroupPath(module, newGroup)
    }
    show("${modules.size()} module(s) grouped", "AutoGroupModules", NotificationType.INFORMATION, "AutoGroupModules plugin")
    modifiableModel.commit()
}

def extractMavenInfo(module) {
    file = FilenameIndex.getFilesByName(project, "pom.xml", module.moduleContentScope)
    if (file.size() != 1) {
        return null
    }
    try {
        pomFile = new XmlParser().parse(new File(file[0].virtualFile.path))
    } catch (e) {
        return [:]
    }

    def moduleInfo = [:]

    moduleInfo.artifactId = pomFile.artifactId.text()
    moduleInfo.groupId = pomFile.groupId.text()
    moduleInfo.packaging = pomFile.packaging.text()

    if (!pomFile.parent.isEmpty()) {
        moduleInfo.parent = [:]
        moduleInfo.parent.artifactId = pomFile.parent.packaging.text()
        moduleInfo.parent.groupId = pomFile.parent.packaging.text()
    }

    if (moduleInfo.packaging.isEmpty()) {
        moduleInfo.packaging = "jar"
    }

    return moduleInfo
}
