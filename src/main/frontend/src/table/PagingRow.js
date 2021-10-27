import {Badge, Button, Col, Container, Pagination, Row} from "react-bootstrap";
import {useEffect, useState} from "react";

const PagingRow = ({page, setPage}) => {


  const handleOnClickPrevious = () => {
    if (page !== 1) {
      setPage(page - 1);
    }
  };

  const handleOnClickNext = () => {
    setPage(page + 1)
  };

  return (
    <div>
      <Pagination className="paging-row">
        <Pagination.Item onClick={handleOnClickPrevious}>⟨</Pagination.Item>
        <Pagination.Item>{page}</Pagination.Item>
        <Pagination.Item onClick={handleOnClickNext}>⟩</Pagination.Item>
      </Pagination>
    </div>
  );
};

export default PagingRow;