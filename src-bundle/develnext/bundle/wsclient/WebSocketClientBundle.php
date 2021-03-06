<?php
namespace develnext\bundle\wsclient;

use develnext\bundle\wsclient\components\WebSocketClientComponent;
use ide\bundle\AbstractBundle;
use ide\bundle\AbstractJarBundle;
use ide\formats\ScriptModuleFormat;
use ide\Ide;
use ide\library\IdeLibraryBundleResource;
use ide\project\Project;
use php\io\File;
use php\desktop\Runtime;

class WebSocketClientBundle extends AbstractJarBundle
{
    public function onAdd(Project $project, AbstractBundle $owner = null)
    {
        parent::onAdd($project, $owner);
    }
    public function onRemove(Project $project, AbstractBundle $owner = null){
        parent::onRemove($project, $owner);
    }

    public function onRegister(IdeLibraryBundleResource $resource){
        parent::onRegister($resource);

//        foreach(File::of($resource->getPath())->findFiles() as $file){
//            Runtime::addJar($file);
//        }
    }
}