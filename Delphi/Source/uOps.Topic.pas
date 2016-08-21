unit uOps.Topic;

(**
*
* Copyright (C) 2016 Lennart Andersson.
*
* This file is part of OPS (Open Publish Subscribe).
*
* OPS (Open Publish Subscribe) is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* OPS (Open Publish Subscribe) is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with OPS (Open Publish Subscribe).  If not, see <http://www.gnu.org/licenses/>.
*)

interface

uses uOps.Types,
     uOps.ArchiverInOut,
     uOps.OpsObject;


type
///TODO	class Participant;
//		friend class Domain;
//		friend class Participant;

	TTopic = class(TOPSObject)
  public
    const
      TRANSPORT_MC = 'multicast';
      TRANSPORT_TCP = 'tcp';
      TRANSPORT_UDP = 'udp';

  private
    FName : AnsiString;
		FPort : Integer;
		FTypeID : AnsiString;
    FDomainAddress : AnsiString;
		//FLocalInterface : string;
		FParticipantID : string;
		FDomainID : string;
		//bool reliable;
		FSampleMaxSize : Integer;
		FDeadline : Int64;
		FMinSeparation : Int64;
		FTransport : AnsiString;
		FOutSocketBufferSize : Int64;
		FInSocketBufferSize : Int64;

///TODO		Participant* participant;

		procedure SetSampleMaxSize(Value : Integer);

	public
    constructor Create(namee : AnsiString; portt : Integer; typeIDd : AnsiString; domainAddresss : AnsiString); overload;
		constructor Create; overload;

    procedure Serialize(archiver : TArchiverInOut); override;

    property Name : AnsiString read FName;
    property TypeID : AnsiString read FTypeID;
    property DomainID : string read FDomainID write FDomainID;
    property ParticipantID : string read FParticipantID write FParticipantID;
    property Transport : AnsiString read FTransport write FTransport;
    property DomainAddress : AnsiString read FDomainAddress write FDomainAddress;
    property SampleMaxSize : Integer read FSampleMaxSize write SetSampleMaxSize;
    property Port : Integer read FPort write FPort;
    property OutSocketBufferSize : Int64 read FOutSocketBufferSize write FOutSocketBufferSize;
    property InSocketBufferSize : Int64 read FInSocketBufferSize write FInSocketBufferSize;

///TODO		Participant* getParticipant()
		{
			return participant;
		}

end;

implementation

uses uOps.Exceptions;

constructor TTopic.Create(namee : AnsiString; portt : Integer; typeIDd : AnsiString; domainAddresss : AnsiString);
begin
  inherited Create;
  FName := namee;
  FPort := portt;
  FTypeID := typeIDd;
  FDomainAddress := domainAddresss;
  FParticipantID := 'DEFAULT_PARTICIPANT';
  //reliable(false),
  FSampleMaxSize := uOps.Types.PACKET_MAX_SIZE;
  FDeadline := uOps.Types.MAX_DEADLINE_TIMEOUT;
  FMinSeparation := 0;
  FOutSocketBufferSize := -1;
  FInSocketBufferSize := -1;

  AppendType('Topic');
end;

constructor TTopic.Create;
begin
  inherited;
  FParticipantID := 'DEFAULT_PARTICIPANT';
	//reliable(false),
  FSampleMaxSize := uOps.Types.PACKET_MAX_SIZE;
  FDeadline := uOps.Types.MAX_DEADLINE_TIMEOUT;
  FMinSeparation := 0;
  FOutSocketBufferSize := -1;
  FInSocketBufferSize := -1;

  AppendType('Topic');
end;

procedure TTopic.SetSampleMaxSize(value : Integer);
begin
  if value < uOps.Types.PACKET_MAX_SIZE then begin
    FSampleMaxSize := uOps.Types.PACKET_MAX_SIZE;
  end else begin
    FSampleMaxSize := value;
  end;
end;

procedure TTopic.Serialize(archiver : TArchiverInOut);
var
  tSampleMaxSize : Integer;
begin
  inherited Serialize(archiver);

  archiver.inout('name', FName);
  archiver.inout('dataType', FTypeID);
  archiver.inout('port', FPort);
  archiver.inout('address', FDomainAddress);

  archiver.inout('outSocketBufferSize', FOutSocketBufferSize);
  archiver.inout('inSocketBufferSize', FInSocketBufferSize);

  //Limit this value
  tSampleMaxSize := SampleMaxSize;
  archiver.inout('sampleMaxSize', tSampleMaxSize);
  SampleMaxSize := tSampleMaxSize;

  archiver.inout('transport', FTransport);
  if FTransport = '' then begin
    FTransport := TRANSPORT_MC;
  end else if (FTransport <> TRANSPORT_MC) and (FTransport <> TRANSPORT_TCP) and (FTransport <> TRANSPORT_UDP) then begin
    raise EConfigException('Illegal transport: "' + FTransport +
          '". Transport for topic must be either "multicast", "tcp", "udp" or left blank( = multicast)');
  end;
end;

end.
