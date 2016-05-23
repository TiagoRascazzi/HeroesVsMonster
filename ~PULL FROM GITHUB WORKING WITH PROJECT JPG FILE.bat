git fetch --all
git reset --hard origin/master

SET currentDir=%cd%
ECHO %currentDir% 
bitsadmin.exe /transfer "JGraps project file downloader" https://raw.githubusercontent.com/tia77/HeroesVsMonster/master/HeroesVsMonsters.gpj %currentDir%\HeroesVsMonsters.gpj
