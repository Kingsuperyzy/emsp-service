@echo off
REM Merge split parts back into solution.zip, then unzip to get solution.Msix
REM Usage: double-click this file, or run it in this folder from cmd.exe

copy /b solution.zip.001+solution.zip.002+solution.zip.003+solution.zip.004+solution.zip.005+solution.zip.006+solution.zip.007+solution.zip.008 solution.zip

echo Merge done. Now extracting solution.zip ...
tar -xf solution.zip

echo Done. You should now have solution.Msix in this folder.
pause