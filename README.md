The ComputerNetworkManager, CNM for short, is a program with which computers can be controlled from a central server
and continuously send data to it, which is then processed in a graphical interface.

## Important Links
- Wiki (English) - Coming Soon
- [Wiki (German)](https://github.com/ComputerNetworkManager/WikiDE/wiki)
- Changelog - Coming Soon

## For Contributors
### Version Numbers

#### Semantic Versioning
The CNM follows the [Semantic Versioning 2.0.0](https://semver.org/), witch contains
- MAJOR version witch is used to indicate incompatible API changes,
- MINOR version witch is used to indicate, that functionality was added in a backwards compatible manner and
- PATCH version witch is used to indicate, that backwards compatible bug fixes where made.

If a higher version is increase, all lower versions are reset.
Example: 
```
Current Version: 1.5.16
Increasing minor version results in 1.6
```

#### Additional
In addition to the semantic versioning, there are several changes, witch include:
- if the patch version is 0, it is omitted,
- for special branches there are several labels added witch are
  - "-dev" for development,

  after the label, a version number can be added (e. g. `1.0-dev1` or `1.0-dev1.2`)