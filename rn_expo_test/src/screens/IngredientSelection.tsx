import React, { useState } from 'react';
import { SafeAreaProvider, SafeAreaView } from 'react-native-safe-area-context';
import {StyleSheet, Text, View} from 'react-native';
import { Button, Searchbar, FAB } from "react-native-paper";

export default function App() {

  const [searchQuery, setSearchQuery] = useState('');
  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <Searchbar
          placeholder="Search"
          onChangeText={query => setSearchQuery(query)}
          value={searchQuery}/>

        <FAB
          style={styles.fab}
          icon="arrow-right"
          small={false}
          onPress={() => console.log('Pressed')}
        />
      </SafeAreaView>
    </SafeAreaProvider>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    flexDirection: "column",
    alignItems: "stretch",
    justifyContent: "flex-start",
  },

  search:{
    alignSelf: 'stretch',
    alignContent: 'flex-end'
  },

  fab: {
    position: 'absolute',
    margin: 16,
    right: 0,
    bottom: 0,
  },
});