#!/bin/bash

echo -e "\033[32mCheckout to dev\033[0m"
git checkout dev

echo -e "\033[32mPush to origin dev\033[0m"
git push origin dev
echo -e "\033[32mPush to osc dev\033[0m"
git push osc dev
