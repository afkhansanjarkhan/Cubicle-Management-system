import React, { useEffect, useState } from 'react';
import { StyleSheet } from 'react-native';
import { View } from '../components/Themed';
import { Context } from '../context';
import { RootTabScreenProps } from '../types';
import { ActivityIndicator } from 'react-native-paper';
import { FontAwesome } from '@expo/vector-icons';
import CubicleService from '../services/cubicleService';
import CubicleListComponent from '../components/CubicleListComponent';
import { mapCubicleDetails, mapCubicles } from '../utils/map';
import { CubicleDetailsView } from '../components/CubicleDetailsView';


const cubicleService = new CubicleService();

export default function CubicleLIstScreen({ navigation }: RootTabScreenProps<'Cubicles'>){

  const { state, dispatch } = React.useContext(Context);// doubt
  const [cubicles, setcubicles] = useState([]);
  const [cubicleDetails, setCubicleDetails] = useState<any>([]);
  const [showDetails, setShowDetails] = useState(false);

  React.useLayoutEffect(() => {
    navigation.setOptions({
      headerRight: ({ }) => <FontAwesome
        name='refresh'
        size={23}
        style={styles.refresh}
        onPress={async () => await init()}
      ></FontAwesome>,
    });
  }, [navigation]);

  const init = async () => {
    const response = await cubicleService.search();
    setcubicles(JSON.parse(JSON.stringify(mapCubicles(response))));
  };

  const onClickCubicle = async (data: any) => {
    //Cubicle details

    const response = await cubicleService.fetchCubicle(data.item.pk);
    // console.log('cubicleListScreen');
    // console.log(data.item.pk);

    // setcubicles(mapCubicles(response))
    // setcubicleDetails(mapCubicles(response))
    setCubicleDetails(mapCubicleDetails(response));
    // console.log(response);
    setShowDetails(true);
  };

  const onBackClick = () => {
    setShowDetails(false);
  };


  React.useEffect(() => {
    const unsubscribe = navigation.addListener('focus', async () => {
      await init();
      setShowDetails(false);
    });
    return unsubscribe;
  }, [navigation]);
  if (!cubicles.length) return <ActivityIndicator size='small' color='#0000ff' />;

  return (
    <View style={styles.container}>
      {!showDetails && (
        <CubicleListComponent
          cubicles={cubicles}
          navigation={navigation}
          onClickCubicle={onClickCubicle}
        />
      )}
      {showDetails && (
        <CubicleDetailsView
          cubicles={cubicles}
          cubicleDetails={cubicleDetails}
          onBackClick={onBackClick}
          headerTitle={'Cubicle Details'}
        />
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  refresh: {
    paddingRight: 20,
    color: 'white',
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  separator: {
    marginVertical: 30,
    height: 1,
    width: '80%',
  },
});
