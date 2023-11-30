import React, { Component } from 'react';

import { View, StyleSheet, ScrollView, ViewPropTypes } from 'react-native';
import { Row, Table } from 'react-native-table-component';

export default class TableComponent extends Component {
  constructor(props) {
    super(props);
    this.state = {
      tbHead: ['Header', 'Header', 'Header', 'Header', 'Header', 'Header', 'Header'],
      widths: [100, 120, 130, 140, 150, 160, 170],
    };
  }

  render() {
    const state = this.state;
    const data = [];
    for (let i = 0; i < 50; i += 1) {
      const totlatRows = [];
      for (let j = 0; j < 9; j += 1) {
        totlatRows.push(`${i}${j}`);
      }
      data.push(totlatRows);
    }

    return (
      <View style={styles.container}>
        <ScrollView horizontal={true}>
          <View>
            <Table>
              <Row
                data={state.tbHead}
                widths={state.widths}
                style={styles.headerWrapper}
                textStyle={styles.text}
              />
            </Table>
            <ScrollView style={styles.dataWrapper}>
              <Table>
                {data.map((totlatRows, index) => (
                  <Row
                    key={index}
                    data={totlatRows}
                    textStyle={styles.text}
                    widthArr={state.widths}
                    style={[styles.row, index % 2 && { backgroundColor: '#ffffff' }]}
                  />
                ))}
              </Table>
            </ScrollView>
          </View>
        </ScrollView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 18,
    paddingTop: 25,
  },
  headerWrapper: {
    height: 60,
    backgroundColor: '#BAEFBE',
  },
  text: {
    textAlign: 'center',
  },
  row: {
    height: 60,
    backgroundColor: '#F6F7Fb',
  },
});
