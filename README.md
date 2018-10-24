<p align="center"><img src="https://github.com/XiaofeiTJU/EdgeSim/blob/master/src/main/resources/images/Logo.png"></p>

----
## About EdgeSim
An open-source simulator of edge computing and caching.

----
## Features
This simulator system contains three models: Content, Base Station and User：
* Reflects the local popularity of downloadable content and user preferences
* Content popularity is supported by zipF distribution, size and other system.
Besides, parameters are supported by real data sets.
* Communication model is a large-scale path-loss model.
* Base stations can work together.
* The whole simulation process is continuous in terms of time slices.
* The line chart which can be used directly in the paper can be directly generated. In addition, the simulator stores the result as a file.
* EdgeSim is very scalable

----
## Installation
First of all, you may need to pre-install JDK10. If you want to generate a professional version of the results chart via the emulator directly, you may need to pre-install the python environment and download the matplotlib and numpy.
```python
pip install numpy
```
```python
pip install matplotlib
```
----
## Chat
Welcome everyone to perfect this system, if you have any questions or ideas, please feel free to contact me. [New Issue](https://github.com/XiaofeiTJU/EdgeSim/issues/new).

----
## License
EdgeSim is available under the terms of [the MIT License](https://github.com/XiaofeiTJU/EdgeSim/blob/master/LICENSE).
