package com.example.kursov_project;

public class Client {
 private int inn;
 private String name;
 private String address;
 public Client(int inn, String name, String address){
  this.inn = inn;
  this.name = name;
  this.address = address;
 }

 public String getAddress() {
  return address;
 }

 public void setAddress(String address) {
  this.address = address;
 }

 public String getName() {
  return name;
 }

 public void setName(String name) {
  this.name = name;
 }

 public int getInn() {
  return inn;
 }

 public void setInn(int inn) {
  this.inn = inn;
 }
}
