import React, { useEffect, useState } from 'react';
import { StyleSheet } from 'react-native';
import { View } from '../components/Themed';
import { Context } from '../context';
import { RootTabScreenProps } from '../types';
import NotifService from '../services/notifService';
import DetailInfoService from '../services/detailService';
import ApprovalsService from '../services/approvalService';
import { SET_ALL_NOTIF } from '../constants/constants';
import NotificationScreenView from '../components/NotificationScreenView';
import { ActivityIndicator } from 'react-native-paper';
import { mapBookingDetails, mapCubicleDetails, mapNotifications } from '../utils/map';
import { NotificationsDetailsView } from '../components/NotificationsDetailsView';
import { FontAwesome } from '@expo/vector-icons';
import { BookingServices } from '../services/bookingServices';
import CubicleService from '../services/cubicleService';

const notifService = new NotifService();
const detailInfoService = new DetailInfoService();
const approvalsService = new ApprovalsService();
const bookingServices = new BookingServices();
const cubicleService = new CubicleService();
var headTitle : any;


export default function NotificationsScreen({ navigation }: RootTabScreenProps<'Notifications'>) {
  //const { state, dispatch } = React.useContext(Context);
  const [notifications, setNotifications] = useState([]);
  const [showDetails, setShowDetails] = useState(false);
  const [bookingDetails, setBookingDetails] = useState<any>([]);
  const [bookingId, setBookingId] = useState<any>(null);

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
    const response = await notifService.search();
    setNotifications(JSON.parse(JSON.stringify(mapNotifications(response))));
  };

  

  const onClickNotification = async (data: any) => {
    if (data.item.unread) {
      await notifService.unReadFlagUpdate(data.item.objectIdentifier, data.item.pk);
      init();
    }
    const response = await detailInfoService.fetchDetails(data.item.claimPk);
    setBookingId(data.item.objectIdentifier);
    if(data.item.notifyType==='BOOKING'){
      const res = await bookingServices.getBooking(data.item.objectIdentifier);
      setBookingDetails(mapBookingDetails(res))
      headTitle = 'Booking Details'
    }

    if(data.item.notifyType==='CUBICLE'){
      const response = await cubicleService.fetchCubicle(data.item.objectIdentifier);
      setBookingDetails(mapCubicleDetails(response));
      headTitle ='Cubicle Details'
    }

    setShowDetails(true);
    
    
  };
  const onBackClick = () => {
    setShowDetails(false);
  };

  const onAprovalClick = async (message: string) => {
    await approvalsService.approve(bookingId, message);
    setShowDetails(false);
  };

  const onRejectClick = async (message: string) => {
    await approvalsService.reject(bookingId, message);
   
    setShowDetails(false);
  };

  React.useEffect(() => {
    const unsubscribe = navigation.addListener('focus', async () => {
      await init();
      setShowDetails(false);
    });
    return unsubscribe;
  }, [navigation]);

  if (!notifications.length) return <ActivityIndicator size='small' color='#0000ff' />;

  return (
    <View style={styles.container}>
      {!showDetails && (
        <NotificationScreenView
          notifications={notifications}
          navigation={navigation}
          onClickNotification={onClickNotification}
        />
      )}
      {showDetails && (
        <NotificationsDetailsView
          bookingDetails={bookingDetails}
          onBackClick={onBackClick}
          onClickApprove={onAprovalClick}
          onClickReject={onRejectClick}
          headerTitle={headTitle}
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
