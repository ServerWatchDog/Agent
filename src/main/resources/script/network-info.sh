#!/bin/bash
LANG=c
eth_name=$1
if [ ! $eth_name ]; then
  eth_name=$(ip route get 8.8.8.8 | awk -- '{printf $5}')
fi

link_speed=$(ethtool "$eth_name" 2>/dev/null | grep "Speed:" | cut -d ":" -f 2)
if [ ! $link_speed ]; then
  link_speed="unknown"
fi
link_status=$(ethtool "$eth_name" 2>/dev/null | grep "Link detected:" | cut -d ":" -f 2)
echo -e "name=$eth_name;speed=$link_speed;status=$link_status"
