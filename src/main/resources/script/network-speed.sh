#!/bin/bash
LANG=c
eth_name=$1
if [ ! $eth_name ]; then
  eth_name=$(ip route get 8.8.8.8 | awk -- '{printf $5}')
fi

RX_pre=$(cat /proc/net/dev | grep "$eth_name" | sed 's/:/ /g' | awk '{print $2}')
TX_pre=$(cat /proc/net/dev | grep "$eth_name" | sed 's/:/ /g' | awk '{print $10}')
sleep 1
RX_next=$(cat /proc/net/dev | grep "$eth_name" | sed 's/:/ /g' | awk '{print $2}')
TX_next=$(cat /proc/net/dev | grep "$eth_name" | sed 's/:/ /g' | awk '{print $10}')
RX=$((${RX_next} - ${RX_pre}))
TX=$((${TX_next} - ${TX_pre}))

echo -e "name=$eth_name;up=$RX;down=$TX "
