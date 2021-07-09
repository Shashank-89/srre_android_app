import React from 'react';
import { name as appName } from "./app.json";
import { AppRegistry } from 'react-native';
import MainScreen from "./src/screens/IngredientSelection";
import { Provider as PaperProvider } from "react-native-paper";

export default function App() {
  return (
    <PaperProvider>
        <MainScreen/>
    </PaperProvider>
  );
}

AppRegistry.registerComponent(appName, () => App);