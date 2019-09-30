<h1 align="center">


<p align="center">
  <img src="https://github.com/Tapadoo/Alerter/raw/master/documentation/alert_default.gif" />
</p>


<p align="center">
  <a href="https://www.npmjs.com/package/react-native-notification-banner"><img src="http://img.shields.io/npm/v/react-native-notification-banner.svg?style=flat" /></a>
  <a href="https://github.com/prscX/react-native-notification-banner/pulls"><img alt="PRs Welcome" src="https://img.shields.io/badge/PRs-welcome-brightgreen.svg" /></a>
  <a href="https://github.com/prscX/react-native-notification-banner#License"><img src="https://img.shields.io/npm/l/react-native-notification-banner.svg?style=flat" /></a>
</p>


    ReactNative: Native Notification Banner (Android/iOS)

If this project has helped you out, please support us with a star 🌟
</h1>
A simple lightweight dropdown banner library using creates native capabilities


#### Android


| **[Tapadoo/Alerter](https://github.com/Tapadoo/Alerter)**             |
| ----------------- |


#### iOS

| **[bryx-inc/BRYXBanner](https://github.com/bryx-inc/BRYXBanner)**             |
| ----------------- |


## 📖 Getting started

`$ yarn add react-native-notification-banner`


## **RN60 >= RNNB V1 >**

`$ yarn add react-native-vector-icons`

> RN60 above please use `react-native-notification-banner` V1 and above

- **iOS**

	- Add the following to your `Podfile` -> `ios/Podfile` and run pod update:

	```
  use_native_modules!

  pod 'RNNotificationBanner', :path => '../node_modules/react-native-notification-banner/ios'

  use_frameworks!

  pod 'BRYXBanner', :git => 'https://github.com/prscX/BRYXBanner.git', :branch => 'master'

  post_install do |installer|
    installer.pods_project.targets.each do |target|
      if target.name.include?('BRYXBanner')
        target.build_configurations.each do |config|
          config.build_settings['SWIFT_VERSION'] = '4.2'
        end
      end
    end
  end
	```

- **Android**

Please add below snippet into your app `build.gradle`

```
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

## **RN60 < RNNB V1 <**

> RN60 below please use `react-native-notification-banner` V.0.*

`$ react-native link react-native-notification-banner`

`$ react-native link react-native-vector-icons`

- **iOS**

  - After `react-native link react-native-notification-banner`, please verify `node_modules/react-native-notification-banner/ios/` contains `Pods` folder. If does not exist please execute `pod install` command on `node_modules/react-native-notification-banner/ios/`, if any error => try `pod repo update` then `pod install`
  - After verification, open your project and create a folder 'RNNotificationBanner' under Libraries.
  - Drag `node_modules/react-native-notification-banner/ios/pods/Pods.xcodeproject` into RNNotificationBanner, as well as the RNNotificationBanner.xcodeproject if it does not exist.
  - Add the `BRYXBanner.framework` into your project's `Embedded Binaries` and make sure the framework is also in linked libraries.
  - Go to your project's `Build Settings -> Frameworks Search Path` and add `${BUILT_PRODUCTS_DIR}/BRYXBanner` non-recursive.

  - Now build your iOS app through Xcode

- **Android**

Please add below snippet into your app `build.gradle`

```

buildscript {
    repositories {
        jcenter()
        maven { url "https://maven.google.com" }
		...
    }
	...
}


allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
		maven { url "https://maven.google.com" }
		...
    }
}
```

> **Note:** This library is support on Android 27 > above

## 💻 Usage

```javascript
import { RNNotificationBanner } from 'react-native-notification-banner';

import Icon from 'react-native-vector-icons/FontAwesome'
let copy = <Icon name="copy" size={24} color="#FFFFFF" family={"FontAwesome"} />;

RNNotificationBanner.Show({ title: "Message", subTitle: "Message", withIcon: true, icon: copy})

```

> **Note:**
> - We have added `family` prop for `Icon` class, please make sure that you pass the props


## 💡 API's

**Success**, **Error**, **Info**, **Warn**, **Normal**, **Show**


## 🎨 Props

| Prop              | Type       | Default | Note                                                                                                       |
| ----------------- | ---------- | ------- | ---------------------------------------------------------------------------------------------------------- |
| `title`       | `string`     |         | Specify title of banner
| `subTitle` | `string` |         | Specify subtitle of banner                                                   |  |
| `tintColor` | `HEX-COLOR` |         | Specify tint color of banner                                                   |  |
| `withIcon` | `bool` |         | Enable/Disable icon                                                   |  |
| `icon` | `RNVectorIcon` |         | Specify banner icon                                                  |  |
| `duration` | `int` |         | Specify duration to show banner                                                   |  |
| `enableProgress` | `bool` |    false     | Specify to show progress on banner                                                   |  |
| `onClick`    | `func`     |         | Specify onClick callback                                        |  |
| `onHide`      | `func`     |         | Specify onHide callback



## Icons

- **RN Vector Icons:** It supports [react-native-vector-icons](https://github.com/oblador/react-native-vector-icons) library. Please find below snippet for the usage:

```javascript
	let facebook = <Icon family={'FontAwesome'} name={'facebook'} color={'#000000'} size={30} />

    RNNotificationBanner.Success({ title: "Message", subTitle: "Message", withIcon: true, icon: copy})
```

> **Note:**
> - We have added `family` prop for `Icon` class, please make sure that you pass the props


## ✨ Credits

- [Tapadoo/Alerter](https://github.com/Tapadoo/Alerter)
- [bryx-inc/BRYXBanner](https://github.com/bryx-inc/BRYXBanner)


## 🤔 How to contribute
Have an idea? Found a bug? Please raise to [ISSUES](https://github.com/prscX/react-native-notification-banner/issues).
Contributions are welcome and are greatly appreciated! Every little bit helps, and credit will always be given.

## 💫 Where is this library used?
If you are using this library in one of your projects, add it in this list below. ✨


## 📜 License
This library is provided under the Apache 2 License.

RNBottomActionSheet @ [prscX](https://github.com/prscX)

## 💖 Support my projects
I open-source almost everything I can, and I try to reply everyone needing help using these projects. Obviously, this takes time. You can integrate and use these projects in your applications for free! You can even change the source code and redistribute (even resell it).

However, if you get some profit from this or just want to encourage me to continue creating stuff, there are few ways you can do it:
* Starring and sharing the projects you like 🚀
* If you're feeling especially charitable, please follow [prscX](https://github.com/prscX) on GitHub.

  <a href="https://www.buymeacoffee.com/prscX" target="_blank"><img src="https://www.buymeacoffee.com/assets/img/custom_images/orange_img.png" alt="Buy Me A Coffee" style="height: auto !important;width: auto !important;" ></a>

  Thanks! ❤️
  <br/>
  [prscX.github.io](https://prscx.github.io)
  <br/>
  </ Pranav >