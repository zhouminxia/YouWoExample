'use strict';

import React from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View
} from 'react-native';

class YouWoExample extends React.Component {
  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.hello}>欢迎来到混合开发，123！！</Text>
      </View>
    )
  }
}
var styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
  },
  hello: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
});

AppRegistry.registerComponent('YouWoExample', () => YouWoExample);