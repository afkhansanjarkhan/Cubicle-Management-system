import React, { useEffect, useState } from 'react';
import { StyleSheet } from 'react-native';
import { View } from '../components/Themed';
import { Context } from '../context';
import { RootTabScreenProps } from '../types';
import { ActivityIndicator } from 'react-native-paper';
import { FontAwesome } from '@expo/vector-icons';
import CubicleService from '../services/cubicleService';
import CubicleListComponent from '../components/CubicleListComponent';
import { CubicleDetailsView } from '../components/CubicleDetailsView';
import BookingService from '../services/BookingService';
import BookingListComponent from '../components/BookingListComponent';
import { BookingDetailsView } from '../components/BookingDetailsView';
import { mapBookingDetails, mapBookings } from '../utils/map';


const bookingServices = new BookingService();

export default function BookingListScreen({ navigation }: RootTabScreenProps<'Booking'>){

  const { state, dispatch } = React.useContext(Context);// doubt
  const [bookings, setbooking] = useState([]);
  const [bookingDetails, setBookingDetails] = useState<any>([]);
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
    const response = await bookingServices.search();
    setbooking(JSON.parse(JSON.stringify(mapBookings(response))));
  };

  const onClickBooking = async (data: any) => {
    const response = await bookingServices.getBooking(data.item.pk);
    setBookingDetails(mapBookingDetails(response));
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
  if (!bookings.length) return <ActivityIndicator size='small' color='#0000ff' />;

  return (
    <View style={styles.container}>
      {!showDetails && (
        <BookingListComponent
          bookings={bookings}
          navigation={navigation}
          onClickBooking={onClickBooking}
        />
      )}
      {showDetails && (
        <BookingDetailsView
          bookings={bookings}
          bookingDetails={bookingDetails}
          onBackClick={onBackClick}
          headerTitle={'Booking Details'}
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
