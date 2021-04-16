
var currencyApi = Vue.resource('/currency{/id}');

Vue.component('currency-row', {
    props: ['currency'],
    template:
        '<div>' +
            '{{ currency.numCode }} {{ currency.charCode }} {{ currency.nominal }}' +
            '{{ currency.name }} {{ currency.value }} {{ compute() }}' +
        '</div>',
    methods: {
        compute: function() {
            let sum = this.currency.value - this.currency.previous;
            return sum.toFixed(2);
        }
    }
});

Vue.component('currencies-list', {
  props: ['currencies'],
  template:
        '<div v-bind:style="styleObject">' +
            '<currency-row v-for="currency in currencies" :currency="currency" />' +
        '</div>',
  created: function() {
    currencyApi.get().then(result =>
        result.json().then(data =>
            data.forEach(currency => this.currencies.push(currency))
        )
    )
  }
});

var app = new Vue({
  el: '#app',
  data: {
          styleObject: {
            color: 'red',
            fontSize: '13px'
          }
        },
  template: '<currencies-list :currencies="currencies" />',
  data: {
    currencies: []
  }
});