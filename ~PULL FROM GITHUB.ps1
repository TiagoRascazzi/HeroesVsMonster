
$ScriptDir = Split-Path $script:MyInvocation.MyCommand.Path

Write-Host "Current script directory is $ScriptDir"

Write-Host "Deleting current files"

$start_time = Get-Date
Get-ChildItem -Path  $ScriptDir -Recurse -exclude "~PULL FROM GITHUB.ps1" |
Select -ExpandProperty FullName |
Where {$_ -notlike '$ScriptDir\.git'} |
sort length -Descending |
Remove-Item -force 

Write-Host "DONE in $((Get-Date).Subtract($start_time).Seconds) second(s)"
Write-Host ""
Write-Host "Pulling from GITHUB"
$start_time = Get-Date
git fetch --all
git reset --hard origin/master

Write-Host "DONE in $((Get-Date).Subtract($start_time).Seconds) second(s)"
Write-Host ""
Write-Host "Downloading JGRASP project file"

$url = "https://raw.githubusercontent.com/tia77/HeroesVsMonster/master/HeroesVsMonsters.gpj"
$output = "$PSScriptRoot\HeroesVsMonsters.gpj"
$start_time = Get-Date

(New-Object System.Net.WebClient).DownloadFile($url, $output)


Write-Host "DONE in $((Get-Date).Subtract($start_time).Seconds) second(s)"

Write-Host "Press any key to continue ..."

$x = $host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")