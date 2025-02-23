import React, { Component } from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  TouchableOpacity
} from 'react-native';

import { RNNotificationBanner } from "react-native-notification-banner";

import Icon from "react-native-vector-icons/FontAwesome";
import AntDesign from "react-native-vector-icons/AntDesign";
import Entypo from "react-native-vector-icons/Entypo";
import EvilIcons from "react-native-vector-icons/EvilIcons";
import Feather from "react-native-vector-icons/Feather";
import FontAwesome from "react-native-vector-icons/FontAwesome";
import FontAwesome5Brands from "react-native-vector-icons/FontAwesome5";
import FontAwesome5Regular from "react-native-vector-icons/FontAwesome5";
import FontAwesome5Solid from "react-native-vector-icons/FontAwesome5";
import Foundation from "react-native-vector-icons/Foundation";
import Ionicons from "react-native-vector-icons/Ionicons";
import MaterialIcons from "react-native-vector-icons/MaterialIcons";
import MaterialCommunityIcons from "react-native-vector-icons/MaterialCommunityIcons";
import SimpleLineIcons from "react-native-vector-icons/SimpleLineIcons";
import Octicons from "react-native-vector-icons/Octicons";
import Zocial from "react-native-vector-icons/Zocial";


// No need to explicitly load fonts individually anymore.  React Native Vector Icons handles this.

interface Props { } // You can define props if your component receives any

interface State { } // You can define state if your component uses state

export default class App extends Component<Props, State> {
  render() {
    const copyIcon = <Feather name="x-circle" size={24} color="#FFFFFF" family="Foundation" />; // Use FontAwesome directly

    return (
      <View style={styles.container}>
        <TouchableOpacity onPress={() => {
          RNNotificationBanner.Normal({ title: "Message", subTitle: "Sub Message" });
        }}>
          <Text>{"Normal"}</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={() => {
          setTimeout(() => {
            RNNotificationBanner.Dismiss();
          }, 2000);

          RNNotificationBanner.Info({
            title: "Message",
            subTitle: "Sub Message",
            duration: 10000,
            enableProgress: true,
            dismissable: false
          });
        }}>
          <Text>{"Info"}</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={() => {
          RNNotificationBanner.Success({ title: 'Message', subTitle: "Sub Message" });
        }}>
          <Text>{'Success'}</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={() => {
          RNNotificationBanner.Warn({ title: 'Message', subTitle: "Sub Message" });
        }}>
          <Text>{'Warning'}</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={() => {
          RNNotificationBanner.Error({ title: 'Message', subTitle: "Sub Message" });
        }}>
          <Text>{'Error'}</Text>
        </TouchableOpacity>

        <TouchableOpacity onPress={() => {
          RNNotificationBanner.Show({
            title: "Message",
            titleSize: 50,
            titleColor: '#555555',
            subTitle: "Message",
            subTitleSize: 50,
            subTitleColor: '#555555',
            withIcon: true,
            duration: 1,
            tintColor: '#000000',
            icon: copyIcon, // Use the icon component
            dismissable: false,
            onClick: () => {
              console.log('on click');
            },
            onHide: () => {
              console.log('on hide');
            }
          });
        }}>
          <Text>{'Custom'}</Text>
        </TouchableOpacity>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  }
});