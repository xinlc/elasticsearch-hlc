#!/bin/bash

echo -e "\033[32mCheckout to main\033[0m"
git checkout main

echo -e "\033[32mMerge dev branch\033[0m"
git merge dev -m 'Prepare release'

echo -e "\033[32mPush to origin main\033[0m"
git push origin main
echo -e "\033[32mPush to osc main\033[0m"
git push osc main
