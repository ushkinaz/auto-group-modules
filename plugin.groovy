import com.intellij.openapi.module.ModuleManager
import com.intellij.psi.search.FilenameIndex

import static liveplugin.PluginUtil.runWriteAction
import static liveplugin.PluginUtil.show

/* ==========================================
 * All configuration is done in Configuration.groovy
 * ==========================================
 */

conf = new Configuration()

mm = ModuleManager.getInstance(project)
modifiableModel = mm.getModifiableModel()
modules = modifiableModel.getModules()

runWriteAction {
    for (module in modules) {
        MavenMetaInfo moduleInfo = extractMavenInfo(module)

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
    show("${modules.size()} modules grouped", "AutoGroup")
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
        return new MavenMetaInfo()
    }

    MavenMetaInfo moduleInfo = new MavenMetaInfo()

    moduleInfo.artifactId = pomFile.artifactId.text()
    moduleInfo.groupId = pomFile.groupId.text()
    moduleInfo.packaging = pomFile.packaging.text()

    if (!pomFile.parent.isEmpty()) {
        moduleInfo.parent = new MavenMetaInfo()
        moduleInfo.parent.artifactId = pomFile.parent.packaging.text()
        moduleInfo.parent.groupId = pomFile.parent.packaging.text()
    }

    if (moduleInfo.packaging.isEmpty()) {
        moduleInfo.packaging = "jar"
    }

    return moduleInfo
}

class MavenMetaInfo {
    MavenMetaInfo parent
    String artifactId
    String groupId
    String packaging

    String toString() {
        return """\
MavenMetaInfo{
    parent=$parent,
    artifactId=$artifactId,
    groupId=$groupId,
    packaging=$packaging
}"""
    }
}
