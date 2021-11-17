## Getting Started

Clone this repository with git. Then enter the directory and fetch submodules:

```git clone https://github.com/hmi-utwente/AsapUnityBridge.git```

```cd AsapUnityBridge```

```git submodule update --init --recursive```


### Configuring the Unity Project
With Unity (tested with version 2017.1), open the folder `AsapUnityBridge/UnityExampleSetup`. 
There is one dependecy, UMA (tested with version 2.6.1) that needs to be downloaded from the [asset store here](https://www.assetstore.unity3d.com/en/#!/content/35611) and imported to the Unity project.

You can open the example scene: `UnityAsapIntegration/ExampleScenes/AsapExampleUMA`

Running this scene doesn't do much. The agent animation is driven by ASAP.

### Configuring ASAP

Download a copy of hmibuild [from the ASAP repository](https://github.com/ArticulatedSocialAgentsPlatform/hmibuild) and place it in the same folder that `AsapUnityBridge` is in. Name it hmibuild (not hmibuild-master).

Enter the `AsapUnityBridge/AsapStarter` folder and run the following commands:

```ant resolve```  
...to download the dependencies, and:

```ant main```  
...to select the main file to run (the default is fine: `nl.utwente.hmi.starters.UnityAsapStarter`).

```ant run```  
..starts the ASAP backend that drives the character. A little window will pop up where you can type in and execute BML.

**NOTE:** These programs require you to have [Apache ANT](http://ant.apache.org/) installed and in your PATH, as well as [Python 2.7](https://www.python.org/downloads/release/python-2713/).


### Putting it together
Start the Unity scene and the ASAP backend (any order is fine). ASAP will initialize itself for the character in the Unity scene (that may take a moment), then BML can be executed.

## TODO's for this documentation

 - Explain the ASAP infrastructure, and where to find what ([here is a start](http://asap-project.ewi.utwente.nl/))
 - Provide example BMLs.
 - Point to resources for the procedurally generated characters (UMA, [here is a start](http://umawiki.secretanorak.com/Main_Page))
 - Describe how to use your own characters, retargeting, etc.
 - How to use the animation editor and exporting procedural animation files for ASAP gesture binding.
 - Multi character setups.


