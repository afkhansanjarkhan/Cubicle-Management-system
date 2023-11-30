import React, { useEffect, useState } from 'react';
import { StyleSheet } from 'react-native';
import { View } from '../components/Themed';
import { Context } from '../context';
import { RootTabScreenProps } from '../types';
import SearchService from '../services/searchService';
import DetailInfoService from '../services/detailService';
import ApprovalsService from '../services/approvalService';
import { SET_ALL_NOTIF } from '../constants/constants';
import SearchView from '../components/SearchView';
import { ActivityIndicator } from 'react-native-paper';
import { mapBookingDetails, mapDemos, mapNotifications } from '../utils/map';
import { NotificationsDetailsView } from '../components/NotificationsDetailsView';
import { FontAwesome } from '@expo/vector-icons';

const searchService = new SearchService();
const detailInfoService = new DetailInfoService();
const approvalsService = new ApprovalsService();


export default function DemoScreen({ navigation }: RootTabScreenProps<'Notifications'>) {
  //const { state, dispatch } = React.useContext(Context);
  const [demos, setDemos] = useState([]);
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
    const response = await searchService.search('demo');
    setDemos(JSON.parse(JSON.stringify(mapDemos(response))));
  };

  const onClickNotification = async (data: any) => {
    const response = await detailInfoService.fetchDetails(data.item.claimPk);
    setBookingId(data.bookingId);
    setBookingDetails(mapBookingDetails(response))
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

  if (!demos.length) return <ActivityIndicator size='small' color='#0000ff' />;

  return (
    <View style={styles.container}>
      {!showDetails && (
        <SearchView
          data={demos}
          navigation={navigation}
          onClick={onClickNotification}
        />
      )}
      {showDetails && (
        <NotificationsDetailsView
          bookingDetails={bookingDetails}
          onBackClick={onBackClick}
          onClickApprove={onAprovalClick}
          onClickReject={onRejectClick}
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
