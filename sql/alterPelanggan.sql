/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  USER
 * Created: Jul 4, 2019
 */
ALTER TABLE PUBLIC.PELANGGAN ADD PROPINSI VARCHAR(50) DEFAULT '-';
ALTER TABLE PUBLIC.PELANGGAN ADD KABUPATEN VARCHAR(50) DEFAULT '-';
ALTER TABLE PUBLIC.PELANGGAN ADD KECAMATAN VARCHAR(50) DEFAULT '-';
ALTER TABLE PUBLIC.PELANGGAN ADD KELURAHAN VARCHAR(50) DEFAULT '-';
ALTER TABLE PUBLIC.PELANGGAN ADD KODEPOS VARCHAR(10) DEFAULT '-';
ALTER TABLE PUBLIC.PELANGGAN ADD RT VARCHAR(5) DEFAULT '-';
ALTER TABLE PUBLIC.PELANGGAN ADD RW VARCHAR(5) DEFAULT '-';
ALTER TABLE PUBLIC.PELANGGAN ADD NOMOR VARCHAR(5) DEFAULT '-';
ALTER TABLE PUBLIC.PELANGGAN ADD BLOK VARCHAR(5) DEFAULT '-';
ALTER TABLE PUBLIC.PELANGGAN ADD NIK BOOLEAN DEFAULT false;
ALTER TABLE PUBLIC.PELANGGAN ADD ALAMATPEMILIK VARCHAR(500) DEFAULT '';



